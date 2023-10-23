package com.example.infra.executor;

import lombok.extern.slf4j.Slf4j;
import com.example.infra.command.common.CommandResponse;
import lombok.RequiredArgsConstructor;
import com.example.infra.command.factory.CommandFactory;
import com.example.infra.executor.common.CommandReader;
import com.example.infra.executor.common.CommandWriter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class CommandExecutor {
    private final CommandReader commandReader;
    private final CommandWriter commandWriter;

    public void executeCommands() throws IOException {
        var commands = commandReader.read();
        var commandResults = new ArrayList<CommandResponse<?>>();
        for (String cmd : commands) {
            try {
                var command = CommandFactory.createCommand(cmd);
                var result = command.execute();
                commandResults.add(result);
                commandWriter.write(commandResults);
            } catch (Exception ex) {
                log.error("CommandExecutor failed: {}", ex.getMessage());
            }
        }
    }
}
