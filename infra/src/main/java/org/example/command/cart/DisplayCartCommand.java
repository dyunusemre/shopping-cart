package org.example.command.cart;

import org.example.cart.usecase.query.DisplayCart;
import org.example.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cart.model.Cart;
import org.example.command.advice.ThrowsException;
import org.example.command.cart.dto.CartResponse;
import org.example.command.common.Command;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

@Slf4j
@RequiredArgsConstructor
public class DisplayCartCommand implements Command<CartResponse> {
    private final UseCaseHandler<Cart, DisplayCart> displayCartUseCaseHandler;

    @Override
    @ThrowsException
    public CommandResponse<CartResponse> execute() {
        var result = displayCartUseCaseHandler.handle(displayCartUseCase());
        return new CommandResponse<>(Status.SUCCEED, CartResponse.fromCart(result));
    }

    private DisplayCart displayCartUseCase() {
        return new DisplayCart(100);
    }
}
