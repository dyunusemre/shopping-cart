package org.example.promotion.model;

import org.example.cart.model.Cart;
import org.example.promotion.strategy.CategoryPromotionStrategy;
import org.example.promotion.strategy.PromotionStrategy;
import org.example.promotion.strategy.SameSellerPromotionStrategy;
import org.example.promotion.strategy.TotalPricePromotionStrategy;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class Promotion implements DiscountApplier {

    private final Set<PromotionStrategy> promotionStrategies = Set.of(new CategoryPromotionStrategy(),
            new SameSellerPromotionStrategy(),
            new TotalPricePromotionStrategy());

    public void apply(Cart cart) {
        var promotionResultOptional = decideAdvantageous(cart);
        promotionResultOptional.ifPresent(promotionResult -> cart.applyPromotion(promotionResult.toPromotion()));
    }

    private Optional<PromotionResult> decideAdvantageous(Cart cart) {
        var sortedPromotionResults = new TreeSet<>(Comparator.comparingDouble(PromotionResult::totalDiscount));
        promotionStrategies.stream()
                .map(promotionStrategy -> promotionStrategy.calculatePromotionResult(cart))
                .filter(PromotionResult::isApplicable)
                .forEach(sortedPromotionResults::add);

        return sortedPromotionResults.isEmpty() ? Optional.empty() : Optional.of(sortedPromotionResults.last());
    }
}

