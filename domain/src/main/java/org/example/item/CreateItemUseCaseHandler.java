package org.example.item;

import org.example.common.usecase.UseCaseHandler;
import org.example.item.factory.ItemFactory;
import org.example.item.model.Item;
import org.example.item.usecase.command.CreateItem;
import org.example.item.validator.ItemCreationValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateItemUseCaseHandler implements UseCaseHandler<Item, CreateItem> {
    private final ItemCreationValidator itemCreationValidator;

    @Override
    public Item handle(CreateItem useCase) {
        var item = ItemFactory.createItem(useCase.itemId(),
                useCase.cartId(),
                useCase.categoryId(),
                useCase.sellerId(),
                useCase.price(),
                useCase.quantity());
        itemCreationValidator.validate(item);
        return item;
    }
}
