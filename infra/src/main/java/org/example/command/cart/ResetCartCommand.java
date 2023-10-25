package org.example.command.cart;

import org.example.cart.usecase.command.ResetCart;
import org.example.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.command.advice.ThrowsException;
import org.example.command.common.Command;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;
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
