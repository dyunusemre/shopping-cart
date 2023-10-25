package org.example.cart;

import org.example.cart.adapter.FakeCartDataAdapter;
import org.example.cart.usecase.command.RemoveItem;
import org.example.item.model.DefaultItem;
import org.example.item.model.ItemConstants;
import org.example.item.model.SubItem;
import org.example.item.model.VasItem;
import org.example.promotion.model.AppliedPromotion;
import org.example.promotion.model.Promotion;
import org.example.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class RemoveItemUseCaseHandlerTest {
    RemoveItemUseCaseHandler removeItemUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;

    @BeforeEach
    void setUp() {
        var promotionApplier = new Promotion();
        fakeCartDataAdapter = new FakeCartDataAdapter();
        removeItemUseCaseHandler = new RemoveItemUseCaseHandler(fakeCartDataAdapter, promotionApplier);
    }

    @Test
    void should_handle_remove_item_use_case_and_same_promotion_applied() {
        //given
        prepareCartForSamePromotion();
        var removeItem = new RemoveItem(100, 999);
        //when
        removeItemUseCaseHandler.handle(removeItem);
        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart).isNotNull();
        assertThat(cart.getItem(999)).isNotPresent();
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.TotalPrice.getPromotionId());
        assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(500);
        assertThat(cart.getTotalPrice()).isEqualTo(7300);
        assertThat(cart.getTotalAmount()).isEqualTo(6800);
    }

    @Test
    void should_handle_remove_item_use_case_and_changed_promotion_applied() {
        //given
        prepareCartForDifferentPromotion();
        var removeItem = new RemoveItem(100, 999);
        //when
        removeItemUseCaseHandler.handle(removeItem);
        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart).isNotNull();
        assertThat(cart.getItem(999)).isNotPresent();
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.SameSeller.getPromotionId());
        assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(630);
        assertThat(cart.getTotalPrice()).isEqualTo(6300);
        assertThat(cart.getTotalAmount()).isEqualTo(5670);
    }

    private void prepareCartForSamePromotion() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        var item = new DefaultItem(888, 100, 1001, 123, 3000, 2);
        var item2 = new DefaultItem(999, 100, 1001, 456, 1000, 2);
        var item3 = new DefaultItem(1919191, 100, 1001, 897898, 1000, 1);

        SubItem subItem = new VasItem(item, 10101, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 100, 3);
        item.addSubItem(subItem);

        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);
        cart.applyPromotion(new AppliedPromotion(PromotionType.TotalPrice.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }

    private void prepareCartForDifferentPromotion() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        var item = new DefaultItem(888, 100, 1001, 123, 3000, 2);
        var item2 = new DefaultItem(999, 100, 1001, 456, 1000, 2);

        SubItem subItem = new VasItem(item, 10101, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 100, 3);
        item.addSubItem(subItem);

        cart.addItem(item);
        cart.addItem(item2);
        cart.applyPromotion(new AppliedPromotion(PromotionType.TotalPrice.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }
}