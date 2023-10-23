package com.example.infra.command.common;

public record CommandResponse<T>(Status result, T message) {
}
