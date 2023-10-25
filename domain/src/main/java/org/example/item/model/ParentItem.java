package org.example.item.model;

import org.example.common.exception.ExceptionMessage;
import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.validation.SubItemTypeValidation;
import org.example.item.validation.VasItemCountValidation;
import org.example.item.validation.VasItemPriceValidation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class ParentItem extends Item {
    private final Map<Integer, SubItem> subItems;

    public ParentItem(int itemId, int cartId, int categoryId, int sellerId, double price, int quantity) {
        super(itemId, cartId, categoryId, sellerId, price, quantity);
        this.subItems = new HashMap<>();
    }

    public void addSubItem(SubItem subItem) {
        validateSubItemAddition(subItem);
        subItems.merge(subItem.getId(), subItem, (existing, newItem) -> {
            existing.increase(newItem.getQuantity());
            return existing;
        });
    }

    public List<SubItem> getSubItemList() {
        return List.copyOf(subItems.values());
    }

    public int getSubItemCount() {
        return subItems.values().stream().mapToInt(Item::getQuantity).sum();
    }

    public Item getSubItem(int itemId) {
        return Optional.of(subItems.get(itemId))
                .orElseThrow(() -> new ShoppingCartBusinessException(ExceptionMessage.ITEM_NOT_FOUND));
    }

    @Override
    public double getTotalPrice() {
        return super.getTotalPrice() + subItems.values().stream()
                .mapToDouble(Item::getTotalPrice)
                .sum();
    }

    private void validateSubItemAddition(SubItem subItem) {
        var validationChain = new SubItemTypeValidation()
                .addValidation(new VasItemPriceValidation())
                .addValidation(new VasItemCountValidation());

        validationChain.validate(subItem);
    }
}
