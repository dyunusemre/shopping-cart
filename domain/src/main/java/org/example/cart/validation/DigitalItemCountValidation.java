package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.cart.model.CartConstants;
import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.DigitalItem;
import org.example.item.model.Item;

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
