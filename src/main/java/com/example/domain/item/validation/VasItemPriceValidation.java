package com.example.domain.item.validation;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;
import com.example.domain.item.model.VasItem;

public class VasItemPriceValidation extends ItemValidation {
    @Override
    public boolean validate(Item item) {
        if (item instanceof VasItem vasItem) {
            var defaultItem = vasItem.getParentItem();
            if (item.getTotalPrice() > defaultItem.getPrice())
                throw new ShoppingCartBusinessException(ExceptionMessage.VAS_PRICE_EXCEEDED);
        }

        return validateNext(item);
    }
}
