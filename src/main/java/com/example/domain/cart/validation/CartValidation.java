package com.example.domain.cart.validation;

import com.example.domain.cart.model.Cart;
import com.example.domain.item.model.Item;

public abstract class CartValidation {
    private CartValidation nextValidation;

    public abstract boolean validate(Cart cart, Item newItem);

    public CartValidation addValidation(CartValidation validation) {
        if (nextValidation == null) {
            nextValidation = validation;
        } else {
            nextValidation.addValidation(validation);
        }
        return this;
    }

    protected boolean validateNext(Cart cart, Item newItem) {
        if (nextValidation == null) {
            return true;
        }
        return nextValidation.validate(cart, newItem);
    }
}
