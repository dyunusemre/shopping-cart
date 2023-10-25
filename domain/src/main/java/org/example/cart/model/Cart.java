package org.example.cart.model;

import org.example.cart.validation.CartAmountValidation;
import org.example.cart.validation.DigitalItemCountValidation;
import org.example.cart.validation.ItemCountValidation;
import org.example.cart.validation.UniqueItemValidation;
import lombok.Getter;
import org.example.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.example.promotion.model.AppliedPromotion;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class Cart {
    private final int id;
    private final int userId;
    private final Map<Integer, Item> items;
    @Getter
    private AppliedPromotion appliedPromotion;


    public void addItem(Item item) {
        validateAddition(item);
        items.merge(item.getId(), item, (existing, newItem) -> {
            existing.increase(newItem.getQuantity());
            return existing;
        });
    }

    public Optional<Item> getItem(int itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    public List<Item> getItemList() {
        return List.copyOf(items.values());
    }

    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    public void resetCart() {
        items.clear();
        appliedPromotion = null;
    }

    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(Item::getTotalPrice)
                .sum();
    }

    public double getTotalAmount() {
        if (Objects.isNull(appliedPromotion))
            return getTotalPrice();
        return getTotalPrice() - appliedPromotion.totalDiscount();
    }

    public void applyPromotion(AppliedPromotion appliedPromotion) {
        this.appliedPromotion = appliedPromotion;
    }

    private void validateAddition(Item item) {
        var validationChain = new UniqueItemValidation()
                .addValidation(new ItemCountValidation())
                .addValidation(new DigitalItemCountValidation())
                .addValidation(new CartAmountValidation());

        validationChain.validate(this, item);
    }
}
