package com.example.infra.command.item;

import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.item.model.ParentItem;
import com.example.domain.item.model.VasItem;
import com.example.domain.item.usecase.command.AddVasItemToParentItem;
import com.example.domain.item.usecase.command.CreateVasItem;
import com.example.domain.item.usecase.query.RetrieveParentItem;
import com.example.infra.command.advice.ThrowsException;
import lombok.extern.slf4j.Slf4j;
import com.example.infra.command.common.Command;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;
import com.example.infra.command.item.dto.AddVasItemToItemRequest;
import lombok.RequiredArgsConstructor;
import com.example.infra.command.item.dto.CreateVasItemRequest;
import com.example.infra.command.item.dto.RetrieveParentItemRequest;

@Slf4j
@RequiredArgsConstructor
public class AddVasItemToItemCommand implements Command<String> {
    private final UseCaseHandler<ParentItem, RetrieveParentItem> retrieveParentItemUseCaseHandler;
    private final UseCaseHandler<ParentItem, AddVasItemToParentItem> addVasItemToParentItemUseCaseHandler;
    private final UseCaseHandler<VasItem, CreateVasItem> createVasItemUseCaseHandler;
    private final String payload;

    @Override
    @ThrowsException
    public CommandResponse<String> execute() {
        var addItemRequest = AddVasItemToItemRequest.fromPayload(payload);
        var retrieveParentItemRequest = new RetrieveParentItemRequest(addItemRequest.itemId());
        var parentItem = retrieveParentItemUseCaseHandler.handle(retrieveParentItemRequest.toUseCase());
        var createVasItemRequest = createVasItemRequest(parentItem, addItemRequest);
        var vasItem = createVasItemUseCaseHandler.handle(createVasItemRequest.toUseCase());
        addVasItemToParentItemUseCaseHandler.handle(toUseCase(parentItem, vasItem));
        return new CommandResponse<>(Status.SUCCEED, "Vas item added");

    }

    private AddVasItemToParentItem toUseCase(ParentItem item, VasItem vasItem) {
        return new AddVasItemToParentItem(item, vasItem);
    }

    private CreateVasItemRequest createVasItemRequest(ParentItem parentItem, AddVasItemToItemRequest addVasItemToItemRequest) {
        return new CreateVasItemRequest(parentItem,
                addVasItemToItemRequest.vasItemId(),
                addVasItemToItemRequest.vasCategoryId(),
                addVasItemToItemRequest.vasSellerId(),
                addVasItemToItemRequest.price(),
                addVasItemToItemRequest.quantity());
    }
}
