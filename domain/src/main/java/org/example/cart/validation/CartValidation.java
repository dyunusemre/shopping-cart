package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.item.model.Item;

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
