package com.example.infra.command.cart;

import com.example.domain.cart.usecase.command.AddItem;
import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.common.usecase.VoidUseCaseHandler;
import lombok.extern.slf4j.Slf4j;
import com.example.domain.item.model.Item;
import com.example.domain.item.usecase.command.CreateItem;
import com.example.infra.command.advice.ThrowsException;
import com.example.infra.command.cart.dto.AddItemRequest;
import com.example.infra.command.common.Command;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;
import lombok.RequiredArgsConstructor;
import com.example.infra.command.item.dto.CreateItemRequest;

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
