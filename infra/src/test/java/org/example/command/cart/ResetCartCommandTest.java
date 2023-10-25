package org.example.command.cart;

import org.example.cart.ResetCartUseCaseHandler;
import org.example.adapter.FakeExceptionDataPort;
import org.example.adapter.InfraCartFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.command.common.CommandResponse;
import org.example.command.common.Status;

import static org.assertj.core.api.Assertions.assertThat;

class ResetCartCommandTest {
    ResetCartCommand resetCartCommandUnderTest;
    ResetCartUseCaseHandler resetCartUseCaseHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void should_execute_reset_cart_command_success() {
        //given
        resetCartUseCaseHandler = new ResetCartUseCaseHandler(new InfraCartFakeRepository());
        resetCartCommandUnderTest = new ResetCartCommand(resetCartUseCaseHandler);
        //when
        var result = resetCartCommandUnderTest.execute();
        //assert
        assertThat(result).isNotNull()
                .returns(Status.SUCCEED, CommandResponse::result)
                .returns("Cart reset", CommandResponse::message);
    }

    @Test
    void should_execute_reset_cart_command_with_side_effect() {
        //given
        resetCartUseCaseHandler = new ResetCartUseCaseHandler(new FakeExceptionDataPort());
        resetCartCommandUnderTest = new ResetCartCommand(resetCartUseCaseHandler);
        //when
        var result = resetCartCommandUnderTest.execute();
        //assert
        assertThat(result).isNotNull()
                .returns(Status.FAILED, CommandResponse::result)
                .returns("side.effect.exception", CommandResponse::message);
    }
}