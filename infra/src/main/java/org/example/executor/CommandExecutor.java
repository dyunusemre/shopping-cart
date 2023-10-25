package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.example.command.common.CommandResponse;
import lombok.RequiredArgsConstructor;
import org.example.command.factory.CommandFactory;
import org.example.executor.common.CommandReader;
import org.example.executor.common.CommandWriter;

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
