package org.example.command.item;

import org.example.common.usecase.UseCaseHandler;
import org.example.item.model.ParentItem;
import org.example.item.model.VasItem;
import org.example.item.usecase.command.AddVasItemToParentItem;
import org.example.item.usecase.command.CreateVasItem;
import org.example.item.usecase.query.RetrieveParentItem;
import org.example.command.advice.ThrowsException;
import lombok.extern.slf4j.Slf4j;
import org.example.command.common.Command;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;
import org.example.command.item.dto.AddVasItemToItemRequest;
import lombok.RequiredArgsConstructor;
import org.example.command.item.dto.CreateVasItemRequest;
import org.example.command.item.dto.RetrieveParentItemRequest;

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
