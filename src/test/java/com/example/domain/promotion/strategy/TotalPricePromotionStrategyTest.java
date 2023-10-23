package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

class TotalPricePromotionStrategyTest {
    TotalPricePromotionStrategy totalPricePromotionStrategy;

    @BeforeEach
    void setUp() {
        totalPricePromotionStrategy = new TotalPricePromotionStrategy();
    }

    @Test
    void should_calculate_0_discount_price_when_total_price_less_than_500() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 100, 1),
                11, new DefaultItem(11, 1, 10, 10, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = totalPricePromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(0);
    }

    @Test
    void should_calculate_250_discount_when_price_between_500_and_5000() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 100, 5)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = totalPricePromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(250);
    }

    @Test
    void should_calculate_500_discount_when_price_between_5001_and_10000() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 1000, 5),
                11, new DefaultItem(11, 1, 10, 10, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = totalPricePromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(500);
    }

    @Test
    void should_calculate_1000_discount_when_price_between_10001_and_50000() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 9000, 5),
                11, new DefaultItem(11, 1, 10, 10, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = totalPricePromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(1000);
    }

    @Test
    void should_calculate_2000_discount_when_price_more_than_50001() {
        //given
        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, 10, 10, 10000, 5),
                11, new DefaultItem(11, 1, 10, 10, 50, 1)
        );
        var cart = new Cart(100, 1, items);

        //when
        var result = totalPricePromotionStrategy.calculatePromotionResult(cart);
        //then
        Assertions.assertThat(result.totalDiscount()).isEqualTo(2000);
    }


}