package org.example.cart.port;

import org.example.cart.model.Cart;

public interface CartDataPort {
    Cart getShoppingCart(int cartId);

    void saveShoppingCart(Cart cart);
}
