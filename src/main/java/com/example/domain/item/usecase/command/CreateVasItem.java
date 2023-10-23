package com.example.domain.item.usecase.command;

import com.example.domain.common.model.UseCase;
import com.example.domain.item.model.ParentItem;

public record CreateVasItem(ParentItem parentItem, int cartId, int itemId,
                            int categoryId, int sellerId, double price,
                            int quantity) implements UseCase {
}

