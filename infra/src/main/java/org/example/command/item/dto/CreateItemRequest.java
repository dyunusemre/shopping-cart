package org.example.command.item.dto;

import org.example.item.usecase.command.CreateItem;
import org.example.utils.JsonUtil;

public record CreateItemRequest(int itemId, int categoryId, int sellerId,
                                double price, int quantity) {

    public CreateItem toUseCase() {
        return new CreateItem(100,
                itemId,
                categoryId,
                sellerId,
                price,
                quantity);
    }

    public static CreateItemRequest fromPayload(String payload) {
        return JsonUtil.readValue(payload, CreateItemRequest.class);
    }
}
