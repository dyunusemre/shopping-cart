package com.example.domain.promotion.model;

import com.example.domain.cart.model.Cart;

public interface DiscountApplier {
    void apply(Cart cart);
}
