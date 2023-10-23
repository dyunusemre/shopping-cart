package com.example.domain.item.validation;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.item.model.VasItem;

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
