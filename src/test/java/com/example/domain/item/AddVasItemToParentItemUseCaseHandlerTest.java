package com.example.domain.item;

import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.item.model.VasItem;
import com.example.domain.item.usecase.command.AddVasItemToParentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domain.cart.adapter.NoLockAdapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AddVasItemToParentItemUseCaseHandlerTest {
    AddVasItemToParentItemUseCaseHandler addVasItemToParentItemUseCaseHandler;

    @BeforeEach
    void setUp() {
        addVasItemToParentItemUseCaseHandler = new AddVasItemToParentItemUseCaseHandler(new NoLockAdapter());
    }

    @Test
    void should_handle_add_vas_item_use_case() {
        //given
        var parentItem = new DefaultItem(1, 100, 1001, 200, 100.0, 1);
        var vasItem = new VasItem(parentItem, 1, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 30.0, 3);
        var useCase = new AddVasItemToParentItem(parentItem, vasItem);
        //when
        var result = addVasItemToParentItemUseCaseHandler.handle(useCase);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(1);
        assertThat(result.getSubItemCount()).isEqualTo(3);
        assertThat(result.getSubItem(1)).isEqualTo(vasItem);
        assertThat(result.getCartId()).isEqualTo(result.getSubItem(1).getCartId());
        assertThat(result.getTotalPrice()).isEqualTo(190.0);
    }

    @Test
    void should_not_handle_add_vas_item_use_case() {
        //given
        var parentItem = new DefaultItem(1, 100, 1001, 200, 100.0, 1);
        var vasItem = new VasItem(parentItem, 1, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 130.0, 3);
        var useCase = new AddVasItemToParentItem(parentItem, vasItem);
        //when
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> addVasItemToParentItemUseCaseHandler.handle(useCase))
                .withMessage("vas.price.exceeded");
    }
}