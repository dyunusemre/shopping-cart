package com.example.infra.command.cart.dto;

import com.example.domain.cart.usecase.command.RemoveItem;
import com.example.infra.utils.JsonUtil;

public record RemoveItemRequest(int cartId, int itemId) {
    public RemoveItem toUseCase() {
        return new RemoveItem(cartId, itemId);
    }

    public static RemoveItemRequest fromPayload(String payload) {
        return JsonUtil.readValue(payload, RemoveItemRequest.class);
    }
}
