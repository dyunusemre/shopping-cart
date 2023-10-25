package org.example.lock;

import org.example.adapter.FakeCartDataAdapter;
import org.example.cart.AddItemUseCaseHandler;
import org.example.cart.lock.InMemoryLockAdapter;
import org.example.cart.usecase.command.AddItem;
import org.example.item.model.DefaultItem;
import org.example.item.model.Item;
import org.example.promotion.model.Promotion;
import org.example.promotion.model.PromotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.assertj.core.api.Assertions.assertThat;

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