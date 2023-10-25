package org.example.adapter;

import org.example.cart.model.Cart;
import org.example.cart.port.CartDataPort;

import java.util.HashMap;

public class FakeCartDataAdapter implements CartDataPort {
    private Cart cart;

    public FakeCartDataAdapter() {
        cart = new Cart(100, 1, new HashMap<>());
    }

    @Override
    public Cart getShoppingCart(int cartId) {
        return cart;
    }

    @Override
    public void saveShoppingCart(Cart cart) {
        this.cart = cart;
    }
}
