package org.example.cart;

import org.example.cart.port.LockPort;
import org.example.cart.port.CartDataPort;
import org.example.cart.usecase.command.AddItem;
import org.example.common.usecase.VoidUseCaseHandler;
import lombok.RequiredArgsConstructor;
import org.example.promotion.model.DiscountApplier;

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
