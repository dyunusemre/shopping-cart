package org.example.item;

import org.example.cart.port.LockPort;
import org.example.common.usecase.UseCaseHandler;
import org.example.item.model.ParentItem;
import org.example.item.usecase.command.AddVasItemToParentItem;
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
