package com.example.domain.cart.usecase.command;

import com.example.domain.common.model.UseCase;
import com.example.domain.item.model.Item;

public record AddItem(int cartId, Item item) implements UseCase {
}
