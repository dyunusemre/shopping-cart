package com.example.domain.item.validation;

import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.VasItem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemValidationTest {
    @Test
    void should_validate_item_validation_chain() {
        //given
        var validationChain = new SubItemTypeValidation()
                .addValidation(new VasItemPriceValidation())
                .addValidation(new VasItemCountValidation());


        var item = new DefaultItem(1, 100, 1001, 200, 100.0, 1);
        var vasItem = new VasItem(item, 100, 1001, 200, 12, 100, 1);
        //when
        var result = validationChain.validate(vasItem);
        //then
        assertThat(result).isEqualTo(true);
    }
}