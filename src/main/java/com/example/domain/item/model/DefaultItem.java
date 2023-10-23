package com.example.domain.item.model;


public class DefaultItem extends ParentItem {
    public DefaultItem(int itemId, int cartId, int categoryId, int sellerId, double price, int quantity) {
        super(itemId, cartId, categoryId, sellerId, price, quantity);
    }
}
