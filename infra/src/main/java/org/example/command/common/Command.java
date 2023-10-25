package org.example.command.common;

public interface Command<T> {
    CommandResponse<T> execute();
}
