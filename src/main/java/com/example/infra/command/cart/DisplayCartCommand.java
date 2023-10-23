package com.example.infra.command.cart;

import com.example.domain.cart.usecase.query.DisplayCart;
import com.example.domain.common.usecase.UseCaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.domain.cart.model.Cart;
import com.example.infra.command.advice.ThrowsException;
import com.example.infra.command.cart.dto.CartResponse;
import com.example.infra.command.common.Command;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;

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
