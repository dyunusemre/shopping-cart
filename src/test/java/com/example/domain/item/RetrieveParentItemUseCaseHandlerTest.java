package com.example.domain.item;

import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.*;
import com.example.domain.item.usecase.query.RetrieveParentItem;
import com.example.domain.promotion.model.AppliedPromotion;
import com.example.domain.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domain.cart.adapter.FakeCartDataAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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