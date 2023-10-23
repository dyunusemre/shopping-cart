package com.example.infra.command.item.dto;

import com.example.domain.item.model.ParentItem;
import com.example.domain.item.usecase.command.CreateVasItem;

public record CreateVasItemRequest(ParentItem item, int itemId, int categoryId,
                                   int sellerId, double price, int quantity) {


    public CreateVasItem toUseCase() {
        return new CreateVasItem(item, 100, itemId, categoryId, sellerId, price, quantity);
    }
}
