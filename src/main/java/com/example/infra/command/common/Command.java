package com.example.infra.command.common;

public interface Command<T> {
    CommandResponse<T> execute();
}
