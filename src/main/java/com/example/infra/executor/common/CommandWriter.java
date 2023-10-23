package com.example.infra.executor.common;

import com.example.infra.command.common.CommandResponse;

import java.util.List;

public interface CommandWriter {
    void write(List<CommandResponse<?>> commandResponses);
}
