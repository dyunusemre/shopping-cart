package org.example.cart.validation;

import org.example.cart.model.Cart;
import org.example.cart.model.CartConstants;
import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.model.Item;

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
