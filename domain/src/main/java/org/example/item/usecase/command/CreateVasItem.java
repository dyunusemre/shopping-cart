package org.example.item.usecase.command;

import org.example.common.model.UseCase;
import org.example.item.model.ParentItem;

public record CreateVasItem(ParentItem parentItem, int cartId, int itemId,
                            int categoryId, int sellerId, double price,
                            int quantity) implements UseCase {
}

