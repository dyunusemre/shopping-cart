package com.example.domain.cart.model;

import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.DigitalItem;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.promotion.model.AppliedPromotion;
import com.example.domain.promotion.model.PromotionType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = new Cart(100, 1, new HashMap<>());
    }

    @Test
    void should_not_add_same_item_more_than_10() {
        //given
        var item = createDefaultItem(1, 10);
        cart.addItem(item);
        var item2 = createDefaultItem(1, 1);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> cart.addItem(item2))
                .withMessage("cart.max.unique.item.count");
    }

    @Test
    void should_not_add_item_more_than_30() {
        //given
        var item = createDefaultItem(1, 10);
        var item2 = createDefaultItem(2, 10);
        var item3 = createDefaultItem(3, 10);
        var item4 = createDigitalItem(5, 1);
        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> cart.addItem(item4))
                .withMessage("cart.max.item.count");
    }

    @Test
    void should_not_add_more_than_5_digital_item() {
        //given
        var item = createDigitalItem(1, 5);
        cart.addItem(item);
        var item2 = createDigitalItem(1, 1);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> cart.addItem(item2))
                .withMessage("cart.max.digital.item.count");
    }

    @Test
    void should_not_add_item_amount_exceeded_500000() {
        //given
        var item = createDefaultItemWithPrice(1, 1, 500000);
        cart.addItem(item);
        var item2 = createDefaultItemWithPrice(1, 1, 10);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> cart.addItem(item2))
                .withMessage("cart.max.price");
    }

    @Test
    void should_add_item_to_cart() {
        //given
        var item = createDefaultItem(1, 10);
        var item2 = createDigitalItem(2, 5);
        //when
        cart.addItem(item);
        //then
        assertThat(cart.getItemList().size()).isEqualTo(1);

        //when
        cart.addItem(item2);
        //then
        assertThat(cart.getItemList().size()).isEqualTo(2);
    }

    @Test
    void should_merge_item_when_adding_same_item() {
        //given
        var item = createDefaultItem(1, 2);
        var item2 = createDefaultItem(1, 1);
        cart.addItem(item);
        cart.addItem(item2);
        // when
        var result = cart.getItem(1);
        // then
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getQuantity()).isEqualTo(3);
    }

    @Test
    void should_calculate_total_price() {
        //given
        var item = createDefaultItemWithPrice(1, 10, 100.0);
        var item2 = createDigitalItemWithPrice(2, 2, 10.0);
        var item3 = createDigitalItemWithPrice(3, 3, 250.0);
        cart.addItem(item);
        cart.addItem(item2);
        cart.addItem(item3);
        //when
        var result = cart.getTotalPrice();
        //then
        assertThat(result).isEqualTo(1770.0);
    }

    @Test
    void should_get_item_lists() {
        //given
        var item = createDefaultItem(1, 2);
        var item2 = createDigitalItem(2, 1);
        cart.addItem(item);
        cart.addItem(item2);
        // when
        var result = cart.getItemList();
        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void should_reset_cart() {
        //given
        var item = createDefaultItem(1, 2);
        var item2 = createDigitalItem(2, 1);
        cart.addItem(item);
        cart.addItem(item2);
        // when
        cart.resetCart();
        // then
        assertThat(cart.getItemList().size()).isEqualTo(0);
        assertThat(cart.getTotalAmount()).isEqualTo(0);
        assertThat(cart.getTotalPrice()).isEqualTo(0);
        Assertions.assertThat(cart.getAppliedPromotion()).isNull();
    }

    @Test
    void should_remove_item() {
        //given
        var item = createDefaultItemWithPrice(1, 10, 100.0);
        var item2 = createDigitalItemWithPrice(2, 2, 10.0);
        cart.addItem(item);
        cart.addItem(item2);
        //when
        cart.removeItem(1);
        //then
        Assertions.assertThat(cart.getItem(1)).isNotPresent();
        assertThat(cart.getTotalPrice()).isEqualTo(20);
    }

    @Test
    void should_apply_promotion_to_cart() {
        //given
        var item = createDefaultItemWithPrice(1, 10, 100);
        var item2 = createDefaultItemWithPrice(2, 5, 50);
        var promotion = new AppliedPromotion(PromotionType.SameSeller.getPromotionId(), 125);
        cart.addItem(item);
        cart.addItem(item2);
        //when
        cart.applyPromotion(promotion);
        //then
        Assertions.assertThat(cart.getAppliedPromotion()).isNotNull();
        Assertions.assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(9909);
        assertThat(cart.getTotalAmount()).isEqualTo(1125);
    }

    private DefaultItem createDefaultItemWithPrice(int id, int quantity, double price) {
        return new DefaultItem(id, 1, 1001, 200, price, quantity);
    }

    private DigitalItem createDigitalItemWithPrice(int id, int quantity, double price) {
        return new DigitalItem(id, 1, ItemConstants.DIGITAL_CATEGORY_ID, 200, price, quantity);
    }

    private DefaultItem createDefaultItem(int id, int quantity) {
        return new DefaultItem(id, 1, 1001, 200, 30.0, quantity);
    }

    private DigitalItem createDigitalItem(int id, int quantity) {
        return new DigitalItem(id, 1, ItemConstants.DIGITAL_CATEGORY_ID, 200, 30.0, quantity);
    }

}