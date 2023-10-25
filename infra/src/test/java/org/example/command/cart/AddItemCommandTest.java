package org.example.command.cart;

import org.example.adapter.NoLockAdapter;
import org.example.cart.AddItemUseCaseHandler;
import org.example.adapter.InfraCartFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.item.CreateItemUseCaseHandler;
import org.example.item.validator.ItemCreationValidator;
import org.example.promotion.model.Promotion;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

import static org.assertj.core.api.Assertions.assertThat;

class AddItemCommandTest {
    AddItemCommand addItemCommandUnderTest;
    CreateItemUseCaseHandler createItemUseCaseHandler;
    AddItemUseCaseHandler addItemUseCaseHandler;

    @BeforeEach
    void setUp() {
        createItemUseCaseHandler = new CreateItemUseCaseHandler(new ItemCreationValidator());
        addItemUseCaseHandler = new AddItemUseCaseHandler(new InfraCartFakeRepository(), new Promotion(), new NoLockAdapter());
    }

    @Test
    void should_execute_add_item_command_success() {
        //given
        var payload = payload();
        addItemCommandUnderTest = new AddItemCommand(createItemUseCaseHandler, addItemUseCaseHandler, payload);

        //when
        var result = addItemCommandUnderTest.execute();

        //then
        assertThat(result).isNotNull()
                .returns(Status.SUCCEED, CommandResponse::result)
                .returns("Item added", CommandResponse::message);
    }

    @Test
    void should_execute_add_item_command_not_allowed_category_id() {
        //given
        var payload = payloadWithWrongCategory();
        addItemCommandUnderTest = new AddItemCommand(createItemUseCaseHandler, addItemUseCaseHandler, payload);

        //when
        var result = addItemCommandUnderTest.execute();

        //then
        assertThat(result).isNotNull()
                .returns(Status.FAILED, CommandResponse::result)
                .returns("item.seller.not.allowed", CommandResponse::message);
    }

    private String payload() {
        return """
                {
                    "itemId": 888,
                    "categoryId": 1001,
                    "sellerId": 10101,
                    "price": 100.0,
                    "quantity": 3
                }
                """;
    }

    private String payloadWithWrongCategory() {
        return """
                {
                    "itemId": 888,
                    "categoryId": 12312312,
                    "sellerId": 5003,
                    "price": 100.0,
                    "quantity": 3
                }
                """;
    }
}