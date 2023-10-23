package com.example.domain.cart.validation;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.model.CartConstants;
import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;

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
