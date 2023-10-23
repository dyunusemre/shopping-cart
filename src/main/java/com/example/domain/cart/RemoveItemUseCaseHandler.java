package com.example.domain.cart;

import com.example.domain.cart.port.CartDataPort;
import com.example.domain.cart.usecase.command.RemoveItem;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;
import com.example.domain.promotion.model.DiscountApplier;

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
