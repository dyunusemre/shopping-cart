package org.example.promotion.model;

import org.example.cart.model.Cart;

public interface DiscountApplier {
    void apply(Cart cart);
}
