package org.example.promotion.model;

import org.example.cart.model.Cart;
import org.example.item.model.DefaultItem;
import org.example.item.model.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class PromotionTest {
    Promotion promotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion();
    }

    @Test
    void should_apply_category_promotion_to_cart() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 100, 1),
                11, new DefaultItem(11, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 13, 50, 1)
        );
        var cart = new Cart(100, 1, items);
        //when
        promotion.apply(cart);
        //then
        Assertions.assertThat(cart.getAppliedPromotion()).isNotNull();
        Assertions.assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.Category.getPromotionId());
    }

    @Test
    void should_apply_total_price_promotion_to_cart_when_total_price_and_category_applicable() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 4000, 1),
                11, new DefaultItem(11, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 13, 50, 1)
        );
        var cart = new Cart(100, 1, items);
        //when
        promotion.apply(cart);
        //then
        Assertions.assertThat(cart.getAppliedPromotion()).isNotNull();
        Assertions.assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.TotalPrice.getPromotionId());
    }

    @Test
    void should_apply_same_promotion_to_cart_when_total_price_and_category_and_sameseller_applicable() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 4000, 1),
                11, new DefaultItem(11, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 50, 1)
        );
        var cart = new Cart(100, 1, items);
        //when
        promotion.apply(cart);
        //then
        Assertions.assertThat(cart.getAppliedPromotion()).isNotNull();
        Assertions.assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.SameSeller.getPromotionId());
        Assertions.assertThat(cart.getAppliedPromotion().totalDiscount()).isEqualTo(405);
    }

    @Test
    void should_not_apply_promotion_to_cart_when_total_price_and_category_and_sameseller_applicable() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 101, 12, 100, 1),
                11, new DefaultItem(11, 1, 102, 13, 50, 1)
        );
        var cart = new Cart(100, 1, items);
        //when
        promotion.apply(cart);
        //then
        Assertions.assertThat(cart.getAppliedPromotion()).isNull();
    }
}