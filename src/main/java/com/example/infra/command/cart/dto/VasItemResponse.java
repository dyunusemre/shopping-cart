package com.example.infra.command.cart.dto;

import com.example.domain.item.model.SubItem;


public record VasItemResponse(int vasItemId, int vasCategoryId, int vasSellerId,
                              double price, int quantity) {
    public static VasItemResponse fromItem(SubItem item) {
        return new VasItemResponse(item.getId(),
                item.getCategoryId(),
                item.getSellerId(),
                item.getPrice(),
                item.getQuantity());
    }
}
