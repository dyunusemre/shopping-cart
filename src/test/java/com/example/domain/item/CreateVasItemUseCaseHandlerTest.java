package com.example.domain.item;

import com.example.domain.common.exception.ShoppingCartBusinessException;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.ItemConstants;
import com.example.domain.item.usecase.command.CreateVasItem;
import com.example.domain.item.validator.ItemCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CreateVasItemUseCaseHandlerTest {
    CreateVasItemUseCaseHandler createVasItemUseCaseHandler;

    @BeforeEach
    void setUp() {
        createVasItemUseCaseHandler = new CreateVasItemUseCaseHandler(new ItemCreationValidator());
    }

    @Test
    void should_handle_create_item_use_case() {
        //given
        var parentItem = new DefaultItem(1, 1, 1001, 200, 30.0, 1);
        var useCase = new CreateVasItem(parentItem, 1, 100, ItemConstants.VAS_CATEGORY_ID, ItemConstants.VAS_SELLER_ID, 30.0, 1);
        //when
        var result = createVasItemUseCaseHandler.handle(useCase);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(1);
        assertThat(result.getParentItem()).isEqualTo(parentItem);
    }

    @Test
    void should_not_handle_create_item_use_case_with_not_allowed_seller_id() {
        //given
        var parentItem = new DefaultItem(1, 1, 1001, 200, 30.0, 1);
        var useCase = new CreateVasItem(parentItem, 1, 100, ItemConstants.VAS_CATEGORY_ID, 300, 30.0, 1);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> createVasItemUseCaseHandler.handle(useCase))
                .withMessage("vas.seller.not.allowed");
    }
}