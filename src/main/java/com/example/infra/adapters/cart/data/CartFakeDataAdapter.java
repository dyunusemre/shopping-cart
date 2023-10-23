package com.example.infra.adapters.cart.data;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.port.CartDataPort;

import java.util.HashMap;

public class CartFakeDataAdapter implements CartDataPort {
    private Cart cart = new Cart(100, 1, new HashMap<>());

    private static CartFakeDataAdapter instance = null;

    private CartFakeDataAdapter() {

    }

    public static CartFakeDataAdapter getInstance() {
        if (instance == null) {
            instance = new CartFakeDataAdapter();
        }
        return instance;
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
