package org.example.promotion.strategy;

import org.example.cart.model.Cart;
import org.example.promotion.model.PromotionConstants;
import org.example.promotion.model.PromotionResult;

import static org.example.promotion.model.PromotionType.Category;


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
