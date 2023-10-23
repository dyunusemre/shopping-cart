package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.Item;
import com.example.domain.promotion.model.PromotionConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class CategoryPromotionStrategyTest {
    Cart cart;

    private CategoryPromotionStrategy categoryPromotionStrategy;

    @BeforeEach
    void setUp() {
        categoryPromotionStrategy = new CategoryPromotionStrategy();
    }

    @Test
    void should_calculate_category_promotion() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 100, 1),
                11, new DefaultItem(11, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 13, 50, 1),
                12, new DefaultItem(12, 1, 10, 14, 100, 1)
        );
        cart = new Cart(100, 1, items);
        //when
        var result = categoryPromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(7.5);
    }

}