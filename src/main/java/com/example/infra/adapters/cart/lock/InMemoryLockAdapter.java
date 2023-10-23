package com.example.infra.adapters.cart.lock;

import com.example.domain.cart.port.LockPort;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class InMemoryLockAdapter implements LockPort {
    private final ConcurrentHashMap<Integer, Lock> inMemoryLock = new ConcurrentHashMap<>();

    private static InMemoryLockAdapter instance = null;

    private InMemoryLockAdapter() {
    }

    public static synchronized InMemoryLockAdapter getInstance() {
        if (instance == null) {
            instance = new InMemoryLockAdapter();
        }
        return instance;
    }

    @Override
    public void lock(int cartId) {
        var reetrantLock = inMemoryLock.computeIfAbsent(cartId, id -> new ReentrantLock());
        reetrantLock.lock();
        log.info("Lock acquired for cartId: {}", cartId);
    }

    @Override
    public void unlock(int cartId) {
        Lock lock = inMemoryLock.get(cartId);
        if (lock != null) {
            lock.unlock();
            log.info("Lock released for cartId: {}", cartId);
        }
    }
}
