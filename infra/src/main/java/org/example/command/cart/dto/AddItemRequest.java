package org.example.command.cart.dto;

import org.example.cart.usecase.command.AddItem;
import org.example.item.model.Item;

public record AddItemRequest(int cartId, Item item) {

    public AddItem toUseCase() {
        return new AddItem(cartId, item);
    }

}
