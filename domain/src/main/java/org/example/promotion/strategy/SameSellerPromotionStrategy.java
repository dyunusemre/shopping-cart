package org.example.promotion.strategy;

import org.example.cart.model.Cart;
import org.example.item.model.Item;
import org.example.promotion.model.PromotionConstants;
import org.example.promotion.model.PromotionResult;

import static org.example.promotion.model.PromotionType.SameSeller;


public class SameSellerPromotionStrategy implements PromotionStrategy {
    @Override
    public PromotionResult calculatePromotionResult(Cart cart) {
        var isSameSellerApplicable = cart.getItemList()
                .stream().map(Item::getSellerId)
                .distinct()
                .count() == 1;
        if (!isSameSellerApplicable)
            return new PromotionResult(0, SameSeller.getPromotionId(), false);

        var totalDiscount = cart.getTotalPrice() * PromotionConstants.SAME_SELLER_MULTIPLIER;
        return new PromotionResult(totalDiscount, SameSeller.getPromotionId(), true);
    }
}
