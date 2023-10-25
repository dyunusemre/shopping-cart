package org.example.item.validation;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;
import org.example.item.model.ItemConstants;
import org.example.item.model.SubItem;

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
