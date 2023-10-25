package org.example.command.common;

public record CommandResponse<T>(Status result, T message) {
}
