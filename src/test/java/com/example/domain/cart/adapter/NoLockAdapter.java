package com.example.domain.cart.adapter;

import com.example.domain.cart.port.LockPort;

public class NoLockAdapter implements LockPort {
    @Override
    public void lock(int cartId) {

    }

    @Override
    public void unlock(int cartId) {

    }
}
