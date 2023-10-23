package com.example.domain.cart;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.port.CartDataPort;
import com.example.domain.cart.usecase.query.DisplayCart;
import com.example.domain.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DisplayCartUseCaseHandler implements UseCaseHandler<Cart, DisplayCart> {
    private final CartDataPort cartDataPort;

    @Override
    public Cart handle(DisplayCart useCase) {
        return cartDataPort.getShoppingCart(useCase.cartId());
    }
}
