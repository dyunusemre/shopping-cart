package com.example.infra.command.factory;

import com.example.domain.cart.AddItemUseCaseHandler;
import com.example.domain.cart.DisplayCartUseCaseHandler;
import com.example.domain.cart.RemoveItemUseCaseHandler;
import com.example.domain.cart.ResetCartUseCaseHandler;
import com.example.domain.item.AddVasItemToParentItemUseCaseHandler;
import com.example.domain.item.CreateItemUseCaseHandler;
import com.example.domain.item.CreateVasItemUseCaseHandler;
import com.example.domain.item.RetrieveParentItemUseCaseHandler;
import com.example.domain.item.validator.ItemCreationValidator;
import com.example.domain.promotion.model.Promotion;
import com.example.infra.adapters.cart.data.CartFakeDataAdapter;
import com.example.infra.adapters.cart.lock.InMemoryLockAdapter;
import com.example.infra.command.cart.AddItemCommand;
import com.example.infra.command.cart.DisplayCartCommand;
import com.example.infra.command.cart.RemoveItemCommand;
import com.example.infra.command.cart.ResetCartCommand;
import com.example.infra.command.common.Command;
import com.example.infra.command.item.AddVasItemToItemCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.infra.command.common.Request;

public class CommandFactory {
    public static Command<?> createCommand(String event) throws JsonProcessingException {
        var request = Request.fromEvent(event);
        return switch (request.command()) {
            case "addItem" ->
                    new AddItemCommand(new CreateItemUseCaseHandler(new ItemCreationValidator()),
                            new AddItemUseCaseHandler(CartFakeDataAdapter.getInstance(), new Promotion(), InMemoryLockAdapter.getInstance()),
                            request.payload());
            case "displayCart" ->
                    new DisplayCartCommand(new DisplayCartUseCaseHandler(CartFakeDataAdapter.getInstance()));
            case "removeItem" ->
                    new RemoveItemCommand(new RemoveItemUseCaseHandler(CartFakeDataAdapter.getInstance(), new Promotion()), request.payload());
            case "addVasItemToItem" ->
                    new AddVasItemToItemCommand(new RetrieveParentItemUseCaseHandler(CartFakeDataAdapter.getInstance()),
                            new AddVasItemToParentItemUseCaseHandler(InMemoryLockAdapter.getInstance()),
                            new CreateVasItemUseCaseHandler(new ItemCreationValidator()),
                            request.payload());
            case "resetCart" ->
                    new ResetCartCommand(new ResetCartUseCaseHandler(CartFakeDataAdapter.getInstance()));
            default ->
                    throw new RuntimeException(String.format("command.not.supported %s", request.command()));
        };
    }
}
