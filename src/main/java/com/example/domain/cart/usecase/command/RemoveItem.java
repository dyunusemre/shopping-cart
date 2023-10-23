package com.example.domain.cart.usecase.command;

import com.example.domain.common.model.UseCase;

public record RemoveItem(int cartId, int itemId) implements UseCase {
}
