package com.example.domain.item.factory;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.item.model.*;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.*;

public class ItemFactory {
    public static Item createItem(int itemId, int cartId, int categoryId, int sellerId, double price, int quantity) {
        if (categoryId == ItemConstants.DIGITAL_CATEGORY_ID)
            return new DigitalItem(itemId, cartId, categoryId, sellerId, price, quantity);
        else
            return new DefaultItem(itemId, cartId, categoryId, sellerId, price, quantity);
    }

    public static SubItem createSubItem(int itemId, int cartId, int categoryId, int sellerId, double price, int quantity, ParentItem parentItem) {
        if (categoryId == ItemConstants.VAS_CATEGORY_ID)
            return new VasItem(parentItem, itemId, cartId, categoryId, sellerId, price, quantity);

        throw new ShoppingCartBusinessException(ExceptionMessage.SUB_CANNOT_CREATED);
    }
}
