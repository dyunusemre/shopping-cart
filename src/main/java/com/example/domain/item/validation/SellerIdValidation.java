package com.example.domain.item.validation;

import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;
import com.example.domain.item.model.VasItem;

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
