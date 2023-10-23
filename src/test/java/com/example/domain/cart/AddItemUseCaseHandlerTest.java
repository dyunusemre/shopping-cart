package com.example.domain.cart;

import com.example.domain.cart.usecase.command.AddItem;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.Item;
import com.example.domain.promotion.model.AppliedPromotion;
import com.example.domain.promotion.model.Promotion;
import com.example.domain.promotion.model.PromotionConstants;
import com.example.domain.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domain.cart.adapter.FakeCartDataAdapter;
import com.example.domain.cart.adapter.NoLockAdapter;

import static org.assertj.core.api.Assertions.*;

class AddItemUseCaseHandlerTest {
    AddItemUseCaseHandler addItemUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;

    @BeforeEach
    void setUp() {
        var promotionApplier = new Promotion();
        fakeCartDataAdapter = new FakeCartDataAdapter();
        addItemUseCaseHandler = new AddItemUseCaseHandler(fakeCartDataAdapter, promotionApplier, new NoLockAdapter());
        prepareCart(100, 888, 123, 1, 3000);
    }

    @Test
    void should_handle_add_item_use_case_and_same_seller_promotion_applied() {
        //given
        Item item = new DefaultItem(888, 100, PromotionConstants.CATEGORY_DISCOUNT_ID, 123, 3000, 1);
        var addItem = new AddItem(100, item);
        //when
        addItemUseCaseHandler.handle(addItem);
        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart).isNotNull();
        assertThat(cart.getItem(888)).isPresent();
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.SameSeller.getPromotionId());
        assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(600);
        assertThat(cart.getTotalPrice()).isEqualTo(6000);
        assertThat(cart.getTotalAmount()).isEqualTo(5400);
    }

    @Test
    void should_handle_add_item_use_case_and_applied_promotion_change() {
        //given
        Item item = new DefaultItem(123123123, 100, PromotionConstants.CATEGORY_DISCOUNT_ID, 1234, 2000, 1);
        var addItem = new AddItem(100, item);
        //when
        addItemUseCaseHandler.handle(addItem);
        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart.getItem(123123123)).isPresent();
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.TotalPrice.getPromotionId());
        assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(500);
        assertThat(cart.getTotalPrice()).isEqualTo(5000);
        assertThat(cart.getTotalAmount()).isEqualTo(4500);
    }

    @Test
    void should_not_handle_add_item_use_case_when_cart_amount_exceeded() {
        //given
        Item item = new DefaultItem(123, 100, PromotionConstants.CATEGORY_DISCOUNT_ID, 13, 499999, 1);
        var addItem = new AddItem(100, item);
        //when & assert
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> addItemUseCaseHandler.handle(addItem))
                .withMessage("cart.max.price");

        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart.getItem(123)).isNotPresent();
        assertThat(cart.getTotalPrice()).isEqualTo(3000);
    }

    @Test
    void should_not_handle_add_item_use_case_when_item_max_amount_exceeded() {
        //given
        Item item = new DefaultItem(888, 100, PromotionConstants.CATEGORY_DISCOUNT_ID, 1234, 50, 10);
        var addItem = new AddItem(100, item);
        //when & assert
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> addItemUseCaseHandler.handle(addItem))
                .withMessage("cart.max.unique.item.count");

        var cart1 = fakeCartDataAdapter.getShoppingCart(100);
        var itemOpt = cart1.getItem(888);
        assertThat(itemOpt).isPresent();
        assertThat(itemOpt.get().getQuantity()).isEqualTo(1);
        assertThat(cart1.getTotalPrice()).isEqualTo(3000);
    }

    @Test
    void should_not_handle_add_item_use_case_when_total_product_max_amount_exceeded() {
        //given
        addMoreProductToCart();
        Item item = new DefaultItem(6655, 100, PromotionConstants.CATEGORY_DISCOUNT_ID, 1234, 50, 5);
        var addItem = new AddItem(100, item);
        //when & assert
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> addItemUseCaseHandler.handle(addItem))
                .withMessage("cart.max.item.count");

        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart.getItem(6655)).isNotPresent();
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.TotalPrice.getPromotionId());
        assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(500);
        assertThat(cart.getItemList().size()).isEqualTo(4);
    }

    private void prepareCart(int cartId, int itemId, int sellerId, int quantity, double price) {
        var cart = fakeCartDataAdapter.getShoppingCart(cartId);
        cart.addItem(new DefaultItem(itemId, cartId, PromotionConstants.CATEGORY_DISCOUNT_ID, sellerId, price, quantity));
        cart.applyPromotion(new AppliedPromotion(PromotionType.SameSeller.getPromotionId(), price * PromotionConstants.SAME_SELLER_MULTIPLIER));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }

    private void addMoreProductToCart() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        Item item1 = new DefaultItem(787, 100, 12345, 33344, 100, 9);
        Item item2 = new DefaultItem(676, 100, 12345, 44333, 100, 9);
        Item item3 = new DefaultItem(345, 100, 12345, 11223, 100, 9);
        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);
        cart.applyPromotion(new AppliedPromotion(PromotionType.TotalPrice.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }

}