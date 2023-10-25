package org.example.adapter;

import org.example.cart.port.CartDataPort;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.cart.model.Cart;

public class FakeExceptionDataPort implements CartDataPort {
    @Override
    public Cart getShoppingCart(int cartId) {
        throw new ShoppingCartBusinessException("side.effect.exception");
    }

    @Override
    public void saveShoppingCart(Cart cart) {

    }
}
