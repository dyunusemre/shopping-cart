package org.example.item.usecase.command;

import org.example.common.model.UseCase;

public record CreateItem(int cartId, int itemId, int categoryId, int sellerId,
                         double price, int quantity) implements UseCase {
}
