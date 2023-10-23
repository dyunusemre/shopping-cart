package com.example.domain.cart.port;

public interface LockPort {
    void lock(int cartId);

    void unlock(int cartId);
}
