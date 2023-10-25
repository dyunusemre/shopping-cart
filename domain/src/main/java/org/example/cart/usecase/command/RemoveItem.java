package org.example.cart.usecase.command;

import org.example.common.model.UseCase;

public record RemoveItem(int cartId, int itemId) implements UseCase {
}
