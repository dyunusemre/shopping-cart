package com.example.domain.item;

import com.example.domain.cart.port.LockPort;
import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.item.model.ParentItem;
import com.example.domain.item.usecase.command.AddVasItemToParentItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddVasItemToParentItemUseCaseHandler implements UseCaseHandler<ParentItem, AddVasItemToParentItem> {
    private final LockPort lockPort;

    @Override
    public ParentItem handle(AddVasItemToParentItem useCase) {
        try {
            lockPort.lock(useCase.parentItem().getCartId());
            var parentItem = useCase.parentItem();
            parentItem.addSubItem(useCase.vasItem());
            return parentItem;
        } finally {
            lockPort.unlock(useCase.parentItem().getCartId());
        }
    }
}
