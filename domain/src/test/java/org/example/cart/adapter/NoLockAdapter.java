package org.example.cart.adapter;

import org.example.cart.port.LockPort;

public class NoLockAdapter implements LockPort {
    @Override
    public void lock(int cartId) {

    }

    @Override
    public void unlock(int cartId) {

    }
}
