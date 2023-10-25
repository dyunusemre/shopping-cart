package org.example.cart;

import org.example.cart.adapter.FakeCartDataAdapter;
import org.example.cart.model.Cart;
import org.example.cart.usecase.query.DisplayCart;
import org.example.item.model.DefaultItem;
import org.example.item.model.ItemConstants;
import org.example.item.model.SubItem;
import org.example.item.model.VasItem;
import org.example.item.model.Item;
import org.example.promotion.model.AppliedPromotion;
import org.example.promotion.model.PromotionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplayCartUseCaseHandlerTest {
    DisplayCartUseCaseHandler displayCartUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;

    @BeforeEach
    void setUp() {
        fakeCartDataAdapter = new FakeCartDataAdapter();
        displayCartUseCaseHandler = new DisplayCartUseCaseHandler(fakeCartDataAdapter);
        prepareCart();
    }

    @Test
    void should_handle_display_cart_use_case() {
        //given
        var displayCart = new DisplayCart(100);
        //when
        var result = displayCartUseCaseHandler.handle(displayCart);
        //then
        Assertions.assertThat(result)
                .isNotNull()
                .returns(4800.0, Cart::getTotalAmount)
                .returns(1232, c -> c.getAppliedPromotion().appliedPromotionId())
                .returns(5300.0, Cart::getTotalPrice)
                .returns(2, cart -> cart.getItemList().size())
                .returns(3, cart -> cart.getItemList().stream().mapToInt(Item::getQuantity).sum());
        Assertions.assertThat(result.getItemList().stream()
                        .filter(item -> item instanceof DefaultItem)
                        .mapToInt(item -> ((DefaultItem) item).getSubItemCount())
                        .sum())
                .isEqualTo(3);
    }

    private void prepareCart() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        var item = new DefaultItem(888, 100, 1001, 123, 3000, 1);
        var item2 = new DefaultItem(999, 100, 1001, 456, 1000, 2);

        SubItem subItem = new VasItem(item, 10101, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 100, 3);
        item.addSubItem(subItem);

        cart.addItem(item);
        cart.addItem(item2);
        cart.applyPromotion(new AppliedPromotion(PromotionType.TotalPrice.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }
}