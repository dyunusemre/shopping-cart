package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.item.model.Item;
import com.example.domain.promotion.model.PromotionConstants;
import com.example.domain.promotion.model.PromotionResult;

import static com.example.domain.promotion.model.PromotionType.SameSeller;


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
