package org.example.cart;

import org.example.cart.model.Cart;
import org.example.cart.port.CartDataPort;
import org.example.cart.usecase.query.DisplayCart;
import org.example.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DisplayCartUseCaseHandler implements UseCaseHandler<Cart, DisplayCart> {
    private final CartDataPort cartDataPort;

    @Override
    public Cart handle(DisplayCart useCase) {
        return cartDataPort.getShoppingCart(useCase.cartId());
    }
}
