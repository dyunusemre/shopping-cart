package org.example.command.cart;

import org.example.cart.usecase.command.AddItem;
import org.example.common.usecase.UseCaseHandler;
import org.example.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.item.model.Item;
import org.example.item.usecase.command.CreateItem;
import org.example.command.advice.ThrowsException;
import org.example.command.cart.dto.AddItemRequest;
import org.example.command.common.Command;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;
import lombok.RequiredArgsConstructor;
import org.example.command.item.dto.CreateItemRequest;

@Slf4j
@RequiredArgsConstructor
public class AddItemCommand implements Command<String> {
    private final UseCaseHandler<Item, CreateItem> createItemUseCaseHandler;
    private final VoidUseCaseHandler<AddItem> addItemUseCaseHandler;
    private final String payload;

    @Override
    @ThrowsException
    public CommandResponse<String> execute() {
        var createItemRequest = CreateItemRequest.fromPayload(payload);
        var item = createItemUseCaseHandler.handle(createItemRequest.toUseCase());
        var addItemRequest = new AddItemRequest(100, item);
        addItemUseCaseHandler.handle(addItemRequest.toUseCase());
        return new CommandResponse<>(Status.SUCCEED, "Item added");
    }
}
