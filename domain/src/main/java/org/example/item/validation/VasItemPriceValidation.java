package org.example.item.validation;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;
import org.example.item.model.VasItem;

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
