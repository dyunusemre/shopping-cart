package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.cart.model.CartConstants;
import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;

public class CartAmountValidation extends CartValidation {
    @Override
    public boolean validate(Cart cart, Item newItem) {
        var newTotalAmount = cart.getTotalAmount() + newItem.getTotalPrice();
        if (newTotalAmount > CartConstants.MAX_PRICE)
            throw new ShoppingCartBusinessException(ExceptionMessage.CART_MAX_PRICE);

        return validateNext(cart, newItem);
    }
}
