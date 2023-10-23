package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class SameSellerPromotionStrategyTest {

    SameSellerPromotionStrategy sameSellerPromotionStrategy;

    @BeforeEach
    void setUp() {
        sameSellerPromotionStrategy = new SameSellerPromotionStrategy();
    }

    @Test
    void should_calculate_sameseller_promotion() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 100, 1),
                11, new DefaultItem(11, 1, 10, 10, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = sameSellerPromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(15);
    }

    @Test
    void should_not_calculate_sameseller_promotion() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 100, 1),
                11, new DefaultItem(11, 1, 10, 11, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = sameSellerPromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(0);
    }
}