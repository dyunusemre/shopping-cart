package org.example.executor.common;

import org.example.command.common.CommandResponse;

import java.util.List;

public interface CommandWriter {
    void write(List<CommandResponse<?>> commandResponses);
}
