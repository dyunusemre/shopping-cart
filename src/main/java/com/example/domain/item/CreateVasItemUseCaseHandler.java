package com.example.domain.item;

import com.example.domain.common.usecase.UseCaseHandler;
import com.example.domain.item.factory.ItemFactory;
import com.example.domain.item.model.VasItem;
import com.example.domain.item.validator.ItemCreationValidator;
import com.example.domain.item.usecase.command.CreateVasItem;
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
