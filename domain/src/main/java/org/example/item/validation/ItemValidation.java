package org.example.item.validation;

import org.example.item.model.Item;

public abstract class ItemValidation {
    private ItemValidation nextValidation;

    public abstract boolean validate(Item item);

    public ItemValidation addValidation(ItemValidation validation) {
        if (nextValidation == null) {
            nextValidation = validation;
        } else {
            nextValidation.addValidation(validation);
        }
        return this;
    }

    protected boolean validateNext(Item item) {
        if (nextValidation == null) {
            return true;
        }
        return nextValidation.validate(item);
    }
}
