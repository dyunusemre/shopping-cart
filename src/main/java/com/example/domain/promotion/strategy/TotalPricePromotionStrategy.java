package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.promotion.model.PromotionConstants;
import com.example.domain.promotion.model.PromotionResult;

import static com.example.domain.promotion.model.PromotionType.TotalPrice;


public class TotalPricePromotionStrategy implements PromotionStrategy {
    @Override
    public PromotionResult calculatePromotionResult(Cart cart) {
        var totalDiscount = decideDiscount(cart.getTotalPrice());
        return new PromotionResult(totalDiscount, TotalPrice.getPromotionId(), totalDiscount > 0);
    }

    private double decideDiscount(double totalPrice) {
        if (PromotionConstants.TOTAL_PRICE_LIMIT_500 > totalPrice)
            return PromotionConstants.TOTAL_PRICE_DISCOUNT_0;

        else if (PromotionConstants.TOTAL_PRICE_LIMIT_5000 > totalPrice)
            return PromotionConstants.TOTAL_PRICE_DISCOUNT_250;

        else if (PromotionConstants.TOTAL_PRICE_LIMIT_10000 > totalPrice)
            return PromotionConstants.TOTAL_PRICE_DISCOUNT_500;

        else if (PromotionConstants.TOTAL_PRICE_LIMIT_50000 > totalPrice)
            return PromotionConstants.TOTAL_PRICE_DISCOUNT_1000;

        else
            return PromotionConstants.TOTAL_PRICE_DISCOUNT_2000;
    }
}
