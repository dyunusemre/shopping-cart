package com.example.infra.adapter;

import com.example.domain.cart.port.CartDataPort;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.cart.model.Cart;

public class FakeExceptionDataPort implements CartDataPort {
    @Override
    public Cart getShoppingCart(int cartId) {
        throw new ShoppingCartBusinessException("side.effect.exception");
    }

    @Override
    public void saveShoppingCart(Cart cart) {

    }
}
