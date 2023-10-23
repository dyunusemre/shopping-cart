package com.example.infra.command.cart;

import com.example.domain.cart.usecase.command.RemoveItem;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import com.example.infra.command.advice.ThrowsException;
import com.example.infra.command.cart.dto.RemoveItemRequest;
import com.example.infra.command.common.Command;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class RemoveItemCommand implements Command<String> {
    private final VoidUseCaseHandler<RemoveItem> removeItemUseCaseHandler;
    private final String payload;

    @Override
    @ThrowsException
    public CommandResponse<String> execute() {
        var removeItemRequest = RemoveItemRequest.fromPayload(payload);
        var useCase = removeItemRequest.toUseCase();
        removeItemUseCaseHandler.handle(useCase);
        return new CommandResponse<>(Status.SUCCEED, "Item removed");
    }
}
