package org.example.command.factory;

import org.example.cart.AddItemUseCaseHandler;
import org.example.cart.DisplayCartUseCaseHandler;
import org.example.cart.RemoveItemUseCaseHandler;
import org.example.cart.ResetCartUseCaseHandler;
import org.example.item.AddVasItemToParentItemUseCaseHandler;
import org.example.item.CreateItemUseCaseHandler;
import org.example.item.CreateVasItemUseCaseHandler;
import org.example.item.RetrieveParentItemUseCaseHandler;
import org.example.item.validator.ItemCreationValidator;
import org.example.promotion.model.Promotion;
import org.example.cart.data.CartFakeDataAdapter;
import org.example.cart.lock.InMemoryLockAdapter;
import org.example.command.cart.AddItemCommand;
import org.example.command.cart.DisplayCartCommand;
import org.example.command.cart.RemoveItemCommand;
import org.example.command.cart.ResetCartCommand;
import org.example.command.common.Command;
import org.example.command.item.AddVasItemToItemCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.command.common.Request;

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
