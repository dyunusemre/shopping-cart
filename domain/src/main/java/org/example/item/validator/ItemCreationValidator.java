package org.example.item.validator;

import org.example.item.model.Item;
import org.example.item.validation.SellerIdValidation;

public class ItemCreationValidator {
    public void validate(Item item) {
        var chain = new SellerIdValidation();
        chain.validate(item);
    }
}
