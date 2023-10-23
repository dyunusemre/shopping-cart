package com.example.infra.command.cart;

import com.example.domain.cart.usecase.command.ResetCart;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import com.example.infra.command.advice.ThrowsException;
import com.example.infra.command.common.Command;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class ResetCartCommand implements Command<String> {
    private final VoidUseCaseHandler<ResetCart> resetCartUseCaseHandler;

    @Override
    @ThrowsException
    public CommandResponse<String> execute() {
        resetCartUseCaseHandler.handle(resetCartUseCase());
        return new CommandResponse<>(Status.SUCCEED, "Cart reset");
    }

    private ResetCart resetCartUseCase() {
        return new ResetCart(100);
    }
}
