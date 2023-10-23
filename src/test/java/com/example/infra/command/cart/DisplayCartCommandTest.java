package com.example.infra.command.cart;


import com.example.domain.cart.DisplayCartUseCaseHandler;
import com.example.infra.adapter.FakeExceptionDataPort;
import com.example.infra.adapter.InfraCartFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domain.promotion.model.PromotionType;
import com.example.infra.command.common.CommandResponse;
import com.example.infra.command.common.Status;

import static org.assertj.core.api.Assertions.assertThat;

class DisplayCartCommandTest {
    DisplayCartCommand displayCartCommandUnderTest;
    DisplayCartUseCaseHandler displayCartUseCaseHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    void should_execute_display_cart_command() {
        //given
        displayCartUseCaseHandler = new DisplayCartUseCaseHandler(new InfraCartFakeRepository());
        displayCartCommandUnderTest = new DisplayCartCommand(displayCartUseCaseHandler);
        //when
        var result = displayCartCommandUnderTest.execute();
        //then
        assertThat(result)
                .isNotNull()
                .returns(Status.SUCCEED, CommandResponse::result)
                .returns(PromotionType.TotalPrice.getPromotionId(), c -> c.message().appliedPromotionId())
                .returns(500.0, c -> c.message().totalDiscount())
                .returns(5100.0, c -> c.message().totalAmount())
                .returns(2, c -> c.message().items().size())
        ;
    }

    @Test
    void should_execute_display_cart_command_with_side_effect() {
        //given
        displayCartUseCaseHandler = new DisplayCartUseCaseHandler(new FakeExceptionDataPort());
        displayCartCommandUnderTest = new DisplayCartCommand(displayCartUseCaseHandler);
        //when
        var result = displayCartCommandUnderTest.execute();
        //then
        assertThat(result).isNotNull()
                .returns(Status.FAILED, CommandResponse::result)
                .returns("side.effect.exception", CommandResponse::message);
    }
}