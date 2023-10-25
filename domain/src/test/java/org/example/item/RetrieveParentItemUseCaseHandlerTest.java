package org.example.item;

import org.example.cart.adapter.FakeCartDataAdapter;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.*;
import org.example.item.usecase.query.RetrieveParentItem;
import org.example.promotion.model.AppliedPromotion;
import org.example.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;

class RetrieveParentItemUseCaseHandlerTest {
    RetrieveParentItemUseCaseHandler retrieveParentItemUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;

    @BeforeEach
    void setUp() {
        fakeCartDataAdapter = new FakeCartDataAdapter();
        retrieveParentItemUseCaseHandler = new RetrieveParentItemUseCaseHandler(fakeCartDataAdapter);
        prepareCart();
    }

    @Test
    void should_handle_retrieve_parent_item_use_case() {
        //given
        var retrieveParentItem = new RetrieveParentItem(100, 888);
        //when
        var result = retrieveParentItemUseCaseHandler.handle(retrieveParentItem);
        //then
        assertThat(result)
                .isNotNull()
                .isInstanceOf(ParentItem.class)
                .returns(1001, Item::getCategoryId);
    }

    @Test
    void should_not_handle_retrieve_parent_item_use_case() {
        //given
        var retrieveParentItem = new RetrieveParentItem(100, 123123);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> retrieveParentItemUseCaseHandler.handle(retrieveParentItem))
                .withMessage("item.not.found");
    }

    private void prepareCart() {
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        var item = new DefaultItem(888, 100, 1001, 123, 3000, 1);

        SubItem subItem = new VasItem(item, 10101, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 100, 3);
        item.addSubItem(subItem);

        cart.addItem(item);
        cart.applyPromotion(new AppliedPromotion(PromotionType.SameSeller.getPromotionId(), 500));
        fakeCartDataAdapter.saveShoppingCart(cart);
    }

}