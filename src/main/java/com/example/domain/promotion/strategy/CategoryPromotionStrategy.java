package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.promotion.model.PromotionConstants;
import com.example.domain.promotion.model.PromotionResult;

import static com.example.domain.promotion.model.PromotionType.Category;


public class CategoryPromotionStrategy implements PromotionStrategy {
    @Override
    public PromotionResult calculatePromotionResult(Cart cart) {
        var totalDiscount = cart.getItemList()
                .stream()
                .filter(item -> item.getCategoryId() == PromotionConstants.CATEGORY_DISCOUNT_ID)
                .mapToDouble(item -> item.getTotalPrice() * PromotionConstants.CATEGORY_MULTIPLIER)
                .sum();
        return new PromotionResult(totalDiscount, Category.getPromotionId(), totalDiscount > 0);
    }
}
