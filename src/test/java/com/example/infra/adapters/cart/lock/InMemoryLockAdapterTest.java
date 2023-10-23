package com.example.infra.adapters.cart.lock;

import com.example.domain.cart.AddItemUseCaseHandler;
import com.example.domain.cart.usecase.command.AddItem;
import com.example.domain.item.model.DefaultItem;
import com.example.domain.item.model.Item;
import com.example.domain.promotion.model.Promotion;
import com.example.domain.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.domain.cart.adapter.FakeCartDataAdapter;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.*;

class InMemoryLockAdapterTest {
    AddItemUseCaseHandler addItemUseCaseHandler;
    FakeCartDataAdapter fakeCartDataAdapter;
    InMemoryLockAdapter inMemoryLockAdapter;

    @BeforeEach
    void setUp() {
        inMemoryLockAdapter = InMemoryLockAdapter.getInstance();
        fakeCartDataAdapter = new FakeCartDataAdapter();
        addItemUseCaseHandler = new AddItemUseCaseHandler(fakeCartDataAdapter, new Promotion(), inMemoryLockAdapter);
    }

    @Test
    void should_add_item_to_cart_concurrently() throws InterruptedException {
        //given
        var taskCount = 8;
        var latch = new CountDownLatch(taskCount);
        //when
        for (int i = 0; i < taskCount; i++) {
            Thread thread = new Thread(newAddTask(addItemUseCaseHandler, latch));
            thread.start();
        }
        latch.await();

        //then
        var cart = fakeCartDataAdapter.getShoppingCart(100);
        assertThat(cart.getItem(44466).get().getQuantity()).isEqualTo(8);
        assertThat(cart.getAppliedPromotion().appliedPromotionId()).isEqualTo(PromotionType.SameSeller.getPromotionId());
    }

    private static Runnable newAddTask(AddItemUseCaseHandler addItemUseCaseHandler, CountDownLatch latch) {
        return () -> {
            Item item = new DefaultItem(44466, 100, 333, 123, 1000, 1);
            var addItem = new AddItem(100, item);
            addItemUseCaseHandler.handle(addItem);
            latch.countDown();
        };
    }
}