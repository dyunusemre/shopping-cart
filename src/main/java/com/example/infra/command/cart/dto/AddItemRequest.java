package com.example.infra.command.cart.dto;

import com.example.domain.cart.usecase.command.AddItem;
import com.example.domain.item.model.Item;

public record AddItemRequest(int cartId, Item item) {

    public AddItem toUseCase() {
        return new AddItem(cartId, item);
    }

}
