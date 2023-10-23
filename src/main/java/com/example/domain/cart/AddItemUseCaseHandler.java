package com.example.domain.cart;

import com.example.domain.cart.port.LockPort;
import com.example.domain.cart.port.CartDataPort;
import com.example.domain.cart.usecase.command.AddItem;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;
import com.example.domain.promotion.model.DiscountApplier;

@RequiredArgsConstructor
public class AddItemUseCaseHandler implements VoidUseCaseHandler<AddItem> {
    private final CartDataPort cartDataPort;
    private final DiscountApplier discountApplier;
    private final LockPort lockPort;

    @Override
    public void handle(AddItem useCase) {
        try {
            lockPort.lock(useCase.cartId());
            var cart = cartDataPort.getShoppingCart(useCase.cartId());
            cart.addItem(useCase.item());
            discountApplier.apply(cart);
            cartDataPort.saveShoppingCart(cart);
        } finally {
            lockPort.unlock(useCase.cartId());
        }
    }
}
