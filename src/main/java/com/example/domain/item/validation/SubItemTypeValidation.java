package com.example.domain.item.validation;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.item.model.SubItem;

public class SubItemTypeValidation extends ItemValidation {
    @Override
    public boolean validate(Item item) {
        if (item instanceof SubItem subItem) {
            if (!ItemConstants.SUB_ALLOWED_ITEM_CATEGORIES.contains(subItem.getParentItem().getCategoryId()))
                throw new ShoppingCartBusinessException(ExceptionMessage.SUB_ITEM_NOT_ALLOWED_CATEGORY);
        }
        return validateNext(item);
    }
}
