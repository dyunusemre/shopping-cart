package org.example.promotion.strategy;

import org.example.cart.model.Cart;
import org.example.promotion.model.PromotionResult;

public interface PromotionStrategy {
    PromotionResult calculatePromotionResult(Cart cart);
}
