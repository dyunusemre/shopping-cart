package org.example.command.item.dto;

import org.example.item.model.ParentItem;
import org.example.item.usecase.command.CreateVasItem;

public record CreateVasItemRequest(ParentItem item, int itemId, int categoryId,
                                   int sellerId, double price, int quantity) {


    public CreateVasItem toUseCase() {
        return new CreateVasItem(item, 100, itemId, categoryId, sellerId, price, quantity);
    }
}
