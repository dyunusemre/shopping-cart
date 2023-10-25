package org.example.command.item.dto;

import org.example.item.usecase.query.RetrieveParentItem;

public record RetrieveParentItemRequest(int itemId) {
    public RetrieveParentItem toUseCase() {
        return new RetrieveParentItem(100, itemId);
    }
}
