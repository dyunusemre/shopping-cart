package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.cart.model.CartConstants;
import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;

public class UniqueItemValidation extends CartValidation {
    @Override
    public boolean validate(Cart cart, Item newItem) {
        var uniqueCount = newItem.getQuantity();
        var itemOptional = cart.getItem(newItem.getId());
        if (itemOptional.isPresent())
            uniqueCount += itemOptional.get().getQuantity();

        if (uniqueCount > CartConstants.MAX_UNIQUE_ITEM_COUNT)
            throw new ShoppingCartBusinessException(ExceptionMessage.CART_MAX_UNIQUE_ITEM_COUNT);

        return validateNext(cart, newItem);
    }
}
