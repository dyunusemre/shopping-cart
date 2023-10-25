package org.example.item.model;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Item {
    private final int id;
    private final int cartId;
    private final int categoryId;
    private final int sellerId;
    private final double price;
    private int quantity;

    public double getTotalPrice() {
        return quantity * price;
    }

    public void increase(int quantity) {
        if (quantity <= 0)
            throw new ShoppingCartBusinessException(ExceptionMessage.ITEM_COUNT_CANNOT_DECREASE);

        this.quantity += quantity;
    }
}
