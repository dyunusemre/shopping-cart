package org.example.command.item.dto;

import org.example.utils.JsonUtil;

public record AddVasItemToItemRequest(int itemId, int vasItemId,
                                      int vasCategoryId,
                                      int vasSellerId, double price,
                                      int quantity) {


    public static AddVasItemToItemRequest fromPayload(String payload) {
        return JsonUtil.readValue(payload, AddVasItemToItemRequest.class);
    }
}
