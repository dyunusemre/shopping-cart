package org.example.item.validation;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;
import org.example.item.model.ItemConstants;
import org.example.item.model.VasItem;

public class VasItemCountValidation extends ItemValidation {
    @Override
    public boolean validate(Item item) {
        if (item instanceof VasItem vasItem) {
            var currentTotalVasItemCount = vasItem.getParentItem().getSubItemCount();
            var newCount = vasItem.getQuantity();
            if (newCount + currentTotalVasItemCount > ItemConstants.MAX_VAS_ITEM_COUNT)
                throw new ShoppingCartBusinessException(ExceptionMessage.VAS_ITEM_COUNT_EXCEEDED);
        }

        return validateNext(item);
    }
}
