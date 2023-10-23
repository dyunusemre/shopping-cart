package com.example.domain.item;

import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.item.factory.ItemFactory;
import com.example.domain.item.model.Item;
import com.example.domain.item.usecase.command.CreateItem;
import com.example.domain.item.validator.ItemCreationValidator;
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
