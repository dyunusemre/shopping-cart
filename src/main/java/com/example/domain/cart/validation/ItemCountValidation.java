package com.example.domain.cart.validation;

import com.example.domain.cart.model.Cart;
import com.example.domain.cart.model.CartConstants;
import com.example.domain.common.exception.ExceptionMessage;
import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.Item;

public class ItemCountValidation extends CartValidation {
    @Override
    public boolean validate(Cart cart, Item newItem) {
        var totalItemCount = cart.getItemList()
                .stream()
                .mapToInt(Item::getQuantity)
                .sum();
        if (totalItemCount + newItem.getQuantity() > CartConstants.MAX_ITEM_COUNT)
            throw new ShoppingCartBusinessException(ExceptionMessage.CART_MAX_ITEM_COUNT);

        return validateNext(cart, newItem);
    }

}
