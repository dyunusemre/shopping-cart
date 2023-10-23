package com.example.domain.promotion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PromotionType {
    SameSeller(9909),
    Category(5676),
    TotalPrice(1232);

    private final int promotionId;
}
