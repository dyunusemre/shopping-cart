package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.item.model.DefaultItem;
import org.example.item.model.Item;
import org.example.promotion.model.PromotionConstants;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationTest {

    @Test
    void should_validate_cart_validation_chain() {
        //given
        var validationChain = new CartAmountValidation()
                .addValidation(new ItemCountValidation())
                .addValidation(new UniqueItemValidation())
                .addValidation(new DigitalItemCountValidation());

        Map<Integer, Item> items = Map.of(
                10, new DefaultItem(10, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 12, 100, 1),
                11, new DefaultItem(11, 1, PromotionConstants.CATEGORY_DISCOUNT_ID, 13, 50, 1)
        );
        var cart = new Cart(100, 1, items);
        var item = new DefaultItem(1, 100, 1001, 200, 100.0, 1);
        //when
        var result = validationChain.validate(cart, item);
        //then
        assertThat(result).isEqualTo(true);
    }

}