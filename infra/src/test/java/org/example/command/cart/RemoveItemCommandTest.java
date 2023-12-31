package org.example.command.cart;

import org.example.cart.RemoveItemUseCaseHandler;
import org.junit.jupiter.api.Test;
import org.example.promotion.model.Promotion;
import org.example.adapter.FakeExceptionDataPort;
import org.example.adapter.InfraCartFakeRepository;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

import static org.assertj.core.api.Assertions.assertThat;

class RemoveItemCommandTest {
    RemoveItemCommand removeItemCommandUnderTest;
    RemoveItemUseCaseHandler removeItemUseCaseHandler;

    @Test
    void should_execute_remove_item_command_success() {
        //given
        var payload = payload();
        removeItemUseCaseHandler = new RemoveItemUseCaseHandler(new InfraCartFakeRepository(), new Promotion());
        removeItemCommandUnderTest = new RemoveItemCommand(removeItemUseCaseHandler, payload);
        //when
        var result = removeItemCommandUnderTest.execute();
        //then
        assertThat(result)
                .isNotNull()
                .returns(Status.SUCCEED, CommandResponse::result)
                .returns("Item removed", CommandResponse::message);
    }

    @Test
    void should_execute_remove_item_command_with_side_effect() {
        //given
        var payload = payload();
        removeItemUseCaseHandler = new RemoveItemUseCaseHandler(new FakeExceptionDataPort(), new Promotion());
        removeItemCommandUnderTest = new RemoveItemCommand(removeItemUseCaseHandler, payload);
        //when
        var result = removeItemCommandUnderTest.execute();
        //then
        assertThat(result)
                .isNotNull()
                .returns(Status.FAILED, CommandResponse::result)
                .returns("side.effect.exception", CommandResponse::message);
    }

    private String payload() {
        return """
                {
                    "itemId": 888,
                    "cartId": 100
                }
                """;
    }
}