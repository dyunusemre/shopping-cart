package com.example.infra.command.item.dto;

import com.example.domain.item.usecase.query.RetrieveParentItem;

public record RetrieveParentItemRequest(int itemId) {
    public RetrieveParentItem toUseCase() {
        return new RetrieveParentItem(100, itemId);
    }
}
