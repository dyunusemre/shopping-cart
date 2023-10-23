package com.example.domain.item.model;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import lombok.Getter;

@Getter
public abstract class SubItem extends Item {
    private final ParentItem parentItem;

    public SubItem(ParentItem parentItem, int id, int cartId, int categoryId, int sellerId, double price, int quantity) {
        super(id, cartId, categoryId, sellerId, price, quantity);

        if (parentItem == null)
            throw new ShoppingCartBusinessException(ExceptionMessage.SUB_CANNOT_EXISTS);

        this.parentItem = parentItem;
    }
}
