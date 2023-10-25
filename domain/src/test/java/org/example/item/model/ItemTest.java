package org.example.item.model;

import org.example.common.exception.ShoppingCartBusinessException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ItemTest {
    @Test
    void should_calculate_total_price() {
        //given
        var item = createItem(5, 1001);
        //when
        var result = item.getTotalPrice();
        //then
        assertThat(result).isEqualTo(150.0);
    }

    @Test
    void should_increase_quantity() {
        //given
        var item = createItem(5, 1001);
        //when
        item.increase(2);
        //then
        assertThat(item.getQuantity()).isEqualTo(7);
    }

    @Test
    void should_not_decrease_quantity() {
        //given
        var item = createItem(5, 1001);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> item.increase(-3))
                .withMessage("item.count.cannot.decrease");
    }

    @Test
    void should_add_sub_item() {
        //given
        var item = createItem(2, 1001);
        var subItem = createSubItem(1, 2, 5, item);
        //when
        item.addSubItem(subItem);
        //then
        assertThat(item.getSubItem(1)).isEqualTo(subItem);
    }

    @Test
    void should_get_total_sub_count() {
        //given
        var item = createItem(2, 1001);
        var subItem = createSubItem(1, 2, 5, item);
        var subItem2 = createSubItem(2, 1, 5, item);
        item.addSubItem(subItem);
        item.addSubItem(subItem2);
        //when
        var result = item.getSubItemCount();
        //then
        assertThat(result).isEqualTo(3);
    }

    @Test
    void should_get_sub_item() {
        //given
        var item = createItem(2, 1001);
        var subItem = createSubItem(1, 2, 5, item);
        item.addSubItem(subItem);
        //when
        var result = item.getSubItem(1);
        //then
        assertThat(result).isEqualTo(subItem);
    }

    @Test
    void should_increase_quantity_same_sub_item() {
        //given
        var item = createItem(2, 1001);
        var subItem = createSubItem(1, 1, 5, item);
        item.addSubItem(subItem);
        //when
        var subItem2 = createSubItem(1, 2, 5, item);
        item.addSubItem(subItem2);
        //then
        assertThat(item.getSubItem(1).getQuantity()).isEqualTo(3);
    }

    @Test
    void should_not_add_sub_item_when_category_is_not_allowed() {
        //given
        var item = createItem(1, 1002);
        //when & then
        var subItem = createSubItem(1, 2, 5, item);

        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> item.addSubItem(subItem))
                .withMessage("sub.item.not.allowed.category");

    }

    @Test
    void should_not_add_when_vas_item_count_exceeded() {
        //given
        var item = createItem(1, 1001);
        var vasItem = createSubItem(1, 4, 5, item);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> item.addSubItem(vasItem))
                .withMessage("vas.item.count.exceeded");

    }

    @Test
    void should_not_add_when_vas_item_price_exceeded() {
        //given
        var item = createItem(1, 1001);
        var vasItem = createSubItem(1, 1, 50, item);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> item.addSubItem(vasItem))
                .withMessage("vas.price.exceeded");

    }

    private DefaultItem createItem(int quantity, int categoryId) {
        return new DefaultItem(1, 1, categoryId, 200, 30.0, quantity);
    }

    private VasItem createSubItem(int id, int quantity, double price, ParentItem item) {
        return new VasItem(item, id, 1, 1212, ItemConstants.VAS_SELLER_ID, price, quantity);
    }

}