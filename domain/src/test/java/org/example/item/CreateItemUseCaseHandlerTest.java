package org.example.item;

import org.example.common.exception.ShoppingCartBusinessException;
import org.example.item.usecase.command.CreateItem;
import org.example.item.validator.ItemCreationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThat;

class CreateItemUseCaseHandlerTest {

    CreateItemUseCaseHandler createItemUseCaseHandler;

    @BeforeEach
    void setUp() {
        createItemUseCaseHandler = new CreateItemUseCaseHandler(new ItemCreationValidator());
    }

    @Test
    void should_handle_create_item_use_case() {
        //given
        var useCase = new CreateItem(1, 1, 1001, 100, 30.0, 1);
        //when
        var result = createItemUseCaseHandler.handle(useCase);
        //then
        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(1);
    }

    @Test
    void should_not_handle_create_item_use_case_with_not_allowed_seller_id() {
        //given
        var useCase = new CreateItem(1, 1, 1001, 5003, 30.0, 1);
        //when & then
        assertThatExceptionOfType(ShoppingCartBusinessException.class)
                .isThrownBy(() -> createItemUseCaseHandler.handle(useCase))
                .withMessage("item.seller.not.allowed");
    }
}