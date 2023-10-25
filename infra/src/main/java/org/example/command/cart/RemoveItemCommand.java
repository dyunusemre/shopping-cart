package org.example.command.cart;

import org.example.cart.usecase.command.RemoveItem;
import org.example.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.command.advice.ThrowsException;
import org.example.command.cart.dto.RemoveItemRequest;
import org.example.command.common.Command;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;
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
