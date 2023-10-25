package org.example.command.item;

import org.example.adapter.NoLockAdapter;
import org.example.cart.port.CartDataPort;
import org.example.item.AddVasItemToParentItemUseCaseHandler;
import org.example.item.CreateVasItemUseCaseHandler;
import org.example.item.RetrieveParentItemUseCaseHandler;
import org.example.item.validator.ItemCreationValidator;
import org.example.adapter.InfraCartFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

import static org.assertj.core.api.Assertions.assertThat;

class AddVasItemToItemCommandTest {
    AddVasItemToItemCommand addVasItemToItemCommandUnderTest;
    RetrieveParentItemUseCaseHandler retrieveParentItemUseCaseHandler;
    AddVasItemToParentItemUseCaseHandler addVasItemToParentItemUseCaseHandler;
    CreateVasItemUseCaseHandler createVasItemUseCaseHandler;

    CartDataPort cartDataPort;

    @BeforeEach
    void setUp() {
        cartDataPort = new InfraCartFakeRepository();
        retrieveParentItemUseCaseHandler = new RetrieveParentItemUseCaseHandler(cartDataPort);
        addVasItemToParentItemUseCaseHandler = new AddVasItemToParentItemUseCaseHandler(new NoLockAdapter());
        createVasItemUseCaseHandler = new CreateVasItemUseCaseHandler(new ItemCreationValidator());
    }

    @Test
    void should_execute_add_vas_item_to_item_command() {
        //given
        var payload = payload();
        addVasItemToItemCommandUnderTest = new AddVasItemToItemCommand(retrieveParentItemUseCaseHandler, addVasItemToParentItemUseCaseHandler, createVasItemUseCaseHandler, payload);
        //when
        var result = addVasItemToItemCommandUnderTest.execute();
        //then
        assertThat(result).isNotNull()
                .returns(Status.SUCCEED, CommandResponse::result)
                .returns("Vas item added", CommandResponse::message);
    }

    @Test
    void should_execute_add_vas_item_to_item_command_with_wrong_seller_id() {
        //given
        var payload = payloadWithWrongSellerId();
        addVasItemToItemCommandUnderTest = new AddVasItemToItemCommand(retrieveParentItemUseCaseHandler, addVasItemToParentItemUseCaseHandler, createVasItemUseCaseHandler, payload);
        //when
        var result = addVasItemToItemCommandUnderTest.execute();
        //then
        assertThat(result).isNotNull()
                .returns(Status.FAILED, CommandResponse::result)
                .returns("vas.seller.not.allowed", CommandResponse::message);
    }

    private String payload() {
        return """
                {
                    "itemId": 999,
                    "vasItemId": 123123,
                    "vasCategoryId": 3242,
                    "vasSellerId": 5003,
                    "price": 100.0,
                    "quantity": 1
                }
                """;
    }

    private String payloadWithWrongSellerId() {
        return """
                {
                    "itemId": 999,
                    "vasItemId": 123123,
                    "vasCategoryId": 3242,
                    "vasSellerId": 23232,
                    "price": 100.0,
                    "quantity": 1
                }
                """;
    }

}