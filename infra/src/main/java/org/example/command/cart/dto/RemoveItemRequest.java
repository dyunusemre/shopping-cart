package org.example.command.cart.dto;

import org.example.cart.usecase.command.RemoveItem;
import org.example.utils.JsonUtil;

public record RemoveItemRequest(int cartId, int itemId) {
    public RemoveItem toUseCase() {
        return new RemoveItem(cartId, itemId);
    }

    public static RemoveItemRequest fromPayload(String payload) {
        return JsonUtil.readValue(payload, RemoveItemRequest.class);
    }
}
