package com.example.domain.cart.validation;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.model.CartConstants;
import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DigitalItem;
import com.example.domain.item.model.Item;

public class DigitalItemCountValidation extends CartValidation {
    @Override
    public boolean validate(Cart cart, Item newItem) {
        if (newItem instanceof DigitalItem digitalItem) {
            var digitalItemCount = cart.getItemList()
                    .stream()
                    .filter(item -> item instanceof DigitalItem)
                    .mapToInt(Item::getQuantity)
                    .sum();
            if (digitalItemCount + digitalItem.getQuantity() > CartConstants.MAX_DIGITAL_ITEM_COUNT)
                throw new ShoppingCartBusinessException(ExceptionMessage.CART_MAX_DIGITAL_ITEM_COUNT);
        }

        return validateNext(cart, newItem);
    }
}
