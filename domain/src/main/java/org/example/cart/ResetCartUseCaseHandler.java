package org.example.cart;

import org.example.cart.port.CartDataPort;
import org.example.cart.usecase.command.ResetCart;
import org.example.common.usecase.VoidUseCaseHandler;
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
