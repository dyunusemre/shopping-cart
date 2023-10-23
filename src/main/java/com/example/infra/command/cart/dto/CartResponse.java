package com.example.infra.command.cart.dto;

import com.example.domain.cart.model.Cart;

import java.util.List;

public record CartResponse(double totalAmount, int appliedPromotionId,
                           double totalDiscount, List<ItemResponse> items) {
    public static CartResponse fromCart(Cart cart) {
        return new CartResponse(cart.getTotalAmount(),
                cart.getAppliedPromotion() != null ? cart.getAppliedPromotion().appliedPromotionId() : 0,
                cart.getAppliedPromotion() != null ? cart.getAppliedPromotion().totalDiscount() : 0,
                cart.getItemList().stream()
                        .map(ItemResponse::fromItem)
                        .toList());
    }
}
