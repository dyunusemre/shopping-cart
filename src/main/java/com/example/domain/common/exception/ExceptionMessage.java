package com.example.domain.common.exception;

public interface ExceptionMessage {
    String VAS_ITEM_COUNT_EXCEEDED = "vas.item.count.exceeded";
    String VAS_PRICE_EXCEEDED = "vas.price.exceeded";
    String SUB_CANNOT_EXISTS = "sub.cannot.exists";
    String SUB_CANNOT_CREATED = "sub.item.cannot.created";
    String VAS_ITEM_CANNOT_ADD = "vas.item.cannot.add";
    String SUB_ITEM_NOT_ALLOWED_CATEGORY = "sub.item.not.allowed.category";
    String ITEM_NOT_FOUND = "item.not.found";
    String ITEM_SELLER_NOT_ALLOWED = "item.seller.not.allowed";
    String ITEM_COUNT_CANNOT_DECREASE = "item.count.cannot.decrease";
    String VAS_SELLER_NOT_ALLOWED = "vas.seller.not.allowed";
    String CART_MAX_PRICE = "cart.max.price";
    String CART_MAX_DIGITAL_ITEM_COUNT = "cart.max.digital.item.count";
    String CART_MAX_ITEM_COUNT = "cart.max.item.count";
    String CART_MAX_UNIQUE_ITEM_COUNT = "cart.max.unique.item.count";
}
