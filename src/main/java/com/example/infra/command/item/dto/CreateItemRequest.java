package com.example.infra.command.item.dto;

import com.example.domain.item.usecase.command.CreateItem;
import com.example.infra.utils.JsonUtil;

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
