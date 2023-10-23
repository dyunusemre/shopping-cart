package com.example.domain.cart;

import com.example.domain.cart.port.CartDataPort;
import com.example.domain.cart.usecase.command.ResetCart;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResetCartUseCaseHandler implements VoidUseCaseHandler<ResetCart> {
    private final CartDataPort cartDataPort;

    @Override
    public void handle(ResetCart useCase) {
        var cart = cartDataPort.getShoppingCart(useCase.cartId());
        cart.resetCart();
        cartDataPort.saveShoppingCart(cart);
    }
}
