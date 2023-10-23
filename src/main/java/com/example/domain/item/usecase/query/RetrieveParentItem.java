package com.example.domain.item.usecase.query;

import com.example.domain.common.model.UseCase;

public record RetrieveParentItem(int cartId, int itemId) implements UseCase {
}
