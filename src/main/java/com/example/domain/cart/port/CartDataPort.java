package com.example.domain.cart.port;

import com.example.domain.cart.model.Cart;

public interface CartDataPort {
    Cart getShoppingCart(int cartId);

    void saveShoppingCart(Cart cart);
}
