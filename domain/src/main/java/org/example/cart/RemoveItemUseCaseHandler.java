package org.example.cart;

import org.example.cart.port.CartDataPort;
import org.example.cart.usecase.command.RemoveItem;
import org.example.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;
import org.example.promotion.model.DiscountApplier;

@RequiredArgsConstructor
public class RemoveItemUseCaseHandler implements VoidUseCaseHandler<RemoveItem> {
    private final CartDataPort cartDataPort;
    private final DiscountApplier discountApplier;

    @Override
    public void handle(RemoveItem useCase) {
        var cart = cartDataPort.getShoppingCart(useCase.cartId());
        cart.removeItem(useCase.itemId());
        discountApplier.apply(cart);
        cartDataPort.saveShoppingCart(cart);
    }
}
