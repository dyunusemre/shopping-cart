package com.example.domain.item.usecase.command;

import com.example.domain.common.model.UseCase;

public record CreateItem(int cartId, int itemId, int categoryId, int sellerId,
                         double price, int quantity) implements UseCase {
}
