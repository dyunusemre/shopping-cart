package org.example.cart.usecase.command;

import org.example.common.model.UseCase;
import org.example.item.model.Item;

public record AddItem(int cartId, Item item) implements UseCase {
}
