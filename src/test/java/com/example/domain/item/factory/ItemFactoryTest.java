package com.example.domain.item.factory;

import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.DigitalItem;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.item.model.VasItem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ItemFactoryTest {

    @Test
    void should_create_digital_item() {
        // when
        var result = ItemFactory.createItem(1, 1, ItemConstants.DIGITAL_CATEGORY_ID, 100, 15.0, 3);
        // then
        Assertions.assertThat(result).isInstanceOf(DigitalItem.class);
    }

    @Test
    void should_create_default_item() {
        // when
        var result = ItemFactory.createItem(1, 1, 100, 100, 15.0, 3);
        // then
        Assertions.assertThat(result).isInstanceOf(DefaultItem.class);
    }

    @Test
    void should_create_vas_item() {
        // given
        var parentItem = new DefaultItem(1, 1, ItemConstants.DIGITAL_CATEGORY_ID, 100, 15.0, 3);
        // when
        var result = ItemFactory.createSubItem(1, 1, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 15.0, 3, parentItem);
        // then
        Assertions.assertThat(result).isInstanceOf(VasItem.class);
    }

    @Test
    void should_throw_exception_when_create_vas_item_wrong_seller_id() {
        // when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> ItemFactory.createSubItem(1, 1, 1, 100, 15.0, 3, null))
                .withMessage("sub.item.cannot.created");
    }

    @Test
    void should_throw_exception_when_create_vas_item_without_parent() {
        // when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> ItemFactory.createSubItem(1, 1, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 15.0, 3, null))
                .withMessage("sub.cannot.exists");
    }
}