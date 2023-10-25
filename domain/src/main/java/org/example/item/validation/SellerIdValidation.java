package org.example.item.validation;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;
import org.example.item.model.VasItem;

public class SellerIdValidation extends ItemValidation {
    @Override
    public boolean validate(Item item) {
        if (item instanceof VasItem vasItem) {
            if (vasItem.getSellerId() != 5003)
                throw new ShoppingCartBusinessException(ExceptionMessage.VAS_SELLER_NOT_ALLOWED);
        } else {
            if (item.getSellerId() == 5003) {
                throw new ShoppingCartBusinessException(ExceptionMessage.ITEM_SELLER_NOT_ALLOWED);
            }
        }
        return validateNext(item);
    }
}
