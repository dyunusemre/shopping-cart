package org.example.cart.usecase.command;

import org.example.common.model.UseCase;

public record ResetCart(int cartId) implements UseCase {
}
