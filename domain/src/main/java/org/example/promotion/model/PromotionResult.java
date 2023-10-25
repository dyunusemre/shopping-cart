package org.example.promotion.model;

public record PromotionResult(double totalDiscount, int promotionId,
                              boolean isApplicable) {

    AppliedPromotion toPromotion() {
        return new AppliedPromotion(promotionId, totalDiscount);
    }
}
