package com.example.domain.cart.usecase.command;

import com.example.domain.common.model.UseCase;

public record ResetCart(int cartId) implements UseCase {
}
