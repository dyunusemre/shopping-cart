package org.example.cart;

import org.example.cart.adapter.FakeCartDataAdapter;
import org.example.cart.model.Cart;
import org.example.cart.usecase.command.ResetCart;
import org.example.item.model.DefaultItem;
import org.example.item.model.ItemConstants;
import org.example.item.model.SubItem;
import org.example.item.model.VasItem;
import org.example.promotion.model.AppliedPromotion;
import org.example.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResetCartUseCaseHandlerTest {
    ResetCartUseCaseHandler resetCartUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;

    @BeforeEach
    void setUp() {
        fakeCartDataAdapter = new FakeCartDataAdapter();
        resetCartUseCaseHandler = new ResetCartUseCaseHandler(fakeCartDataAdapter);
        prepareCart();
    }

    @Test
    void should_handle_reset_cart_use_case() {
        //given
        var resetCart = new ResetCart(100);
        //when
        resetCartUseCaseHandler.handle(resetCart);
        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart)
                .isNotNull()
                .returns(null, Cart::getAppliedPromotion)
                .returns(0.0, Cart::getTotalPrice)
                .returns(0.0, Cart::getTotalAmount)
                .returns(0, c -> c.getItemList().size())
        ;
    }

    private void prepareCart() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        var item = new DefaultItem(888, 100, 1001, 123, 3000, 1);
        var item2 = new DefaultItem(999, 100, 1001, 456, 1000, 2);

        SubItem subItem = new VasItem(item, 10101, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 100, 3);
        item.addSubItem(subItem);

        cart.addItem(item);
        cart.addItem(item2);
        cart.applyPromotion(new AppliedPromotion(PromotionType.SameSeller.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }
}