package org.example.item;

import org.example.cart.port.CartDataPort;
import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.common.usecase.UseCaseHandler;
import org.example.item.model.ParentItem;
import org.example.item.usecase.query.RetrieveParentItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveParentItemUseCaseHandler implements UseCaseHandler<ParentItem, RetrieveParentItem> {
    private final CartDataPort cartDataPort;

    @Override
    public ParentItem handle(RetrieveParentItem useCase) {
        var cart = cartDataPort.getShoppingCart(useCase.cartId());
        var itemOptional = cart.getItem(useCase.itemId());
        return itemOptional
                .map(item -> (ParentItem) item)
                .orElseThrow(() -> new ShoppingCartBusinessException(ExceptionMessage.ITEM_NOT_FOUND));
    }
}
