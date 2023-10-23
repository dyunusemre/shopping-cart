package com.example.domain.promotion.strategy;

import com.example.domain.cart.model.Cart;
import com.example.domain.promotion.model.PromotionResult;

public interface PromotionStrategy {
    PromotionResult calculatePromotionResult(Cart cart);
}
