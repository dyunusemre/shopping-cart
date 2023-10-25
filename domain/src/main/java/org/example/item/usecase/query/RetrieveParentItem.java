package org.example.item.usecase.query;

import org.example.common.model.UseCase;

public record RetrieveParentItem(int cartId, int itemId) implements UseCase {
}
