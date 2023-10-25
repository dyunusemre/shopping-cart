package org.example.adapter;

import org.example.cart.port.CartDataPort;
import org.example.item.model.DefaultItem;
import org.example.cart.model.Cart;
import org.example.item.model.SubItem;
import org.example.item.model.VasItem;
import org.example.promotion.model.AppliedPromotion;
import org.example.promotion.model.PromotionType;

import java.util.HashMap;

public class InfraCartFakeRepository implements CartDataPort {
    private Cart cart;

    public InfraCartFakeRepository() {
        cart = new Cart(100, 1, new HashMap<>());
        var item = new DefaultItem(888, 100, 201, 301, 3000, 1);
        cart.addItem(item);
        var item2 = new DefaultItem(999, 100, 1001, 405, 1000, 2);
        SubItem subItem = new VasItem(item2, 787, 100, 3242, 5003, 300, 2);
        item2.addSubItem(subItem);
        cart.addItem(item2);
        cart.applyPromotion(new AppliedPromotion(PromotionType.TotalPrice.getPromotionId(), 500));
    }

    @Override
    public Cart getShoppingCart(int cartId) {
        return cart;
    }

    @Override
    public void saveShoppingCart(Cart cart) {
        this.cart = cart;
    }
}
