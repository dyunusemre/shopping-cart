package org.example.item;

import org.example.common.usecase.UseCaseHandler;
import org.example.item.factory.ItemFactory;
import org.example.item.model.VasItem;
import org.example.item.validator.ItemCreationValidator;
import org.example.item.usecase.command.CreateVasItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateVasItemUseCaseHandler implements UseCaseHandler<VasItem, CreateVasItem> {
    private final ItemCreationValidator itemCreationValidator;

    @Override
    public VasItem handle(CreateVasItem useCase) {
        var subItem = ItemFactory.createSubItem(useCase.itemId(),
                useCase.cartId(),
                useCase.categoryId(),
                useCase.sellerId(),
                useCase.price(),
                useCase.quantity(),
                useCase.parentItem());
        itemCreationValidator.validate(subItem);
        return (VasItem) subItem;
    }
}
