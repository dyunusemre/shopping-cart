package org.example.item.model;

public class VasItem extends SubItem {
    public VasItem(ParentItem parentItem, int itemId, int cartId, int categoryId, int sellerId, double price, int quantity) {
        super(parentItem, itemId, cartId, categoryId, sellerId, price, quantity);
    }
}
