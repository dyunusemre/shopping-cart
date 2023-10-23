package com.example.domain.item;

import com.example.domain.cart.port.CartDataPort;
import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.item.model.ParentItem;
import com.example.domain.item.usecase.query.RetrieveParentItem;
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
