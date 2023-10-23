package com.example.domain.cart.validation;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.model.CartConstants;
import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;

public class CartAmountValidation extends CartValidation {
    @Override
    public boolean validate(Cart cart, Item newItem) {
        var newTotalAmount = cart.getTotalAmount() + newItem.getTotalPrice();
        if (newTotalAmount > CartConstants.MAX_PRICE)
            throw new ShoppingCartBusinessException(ExceptionMessage.CART_MAX_PRICE);

        return validateNext(cart, newItem);
    }
}
