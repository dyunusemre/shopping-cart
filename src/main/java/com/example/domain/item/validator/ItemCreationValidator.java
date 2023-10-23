package com.example.domain.item.validator;

import com.example.domain.item.model.Item;
import com.example.domain.item.validation.SellerIdValidation;

public class ItemCreationValidator {
    public void validate(Item item) {
        var chain = new SellerIdValidation();
        chain.validate(item);
    }
}
