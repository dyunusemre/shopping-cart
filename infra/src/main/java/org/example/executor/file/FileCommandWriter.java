package org.example.executor.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.command.common.CommandResponse;
import lombok.RequiredArgsConstructor;
import org.example.executor.common.CommandWriter;
import org.example.utils.JsonUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@RequiredArgsConstructor
public class FileCommandWriter implements CommandWriter {
    private final String path;

    public void write(List<CommandResponse<?>> commandResponses) {
        try (var writer = Files.newBufferedWriter(Path.of(path), StandardOpenOption.WRITE)) {
            for (CommandResponse<?> result : commandResponses) {
                writer.write(serializeCommandResult(result));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String serializeCommandResult(CommandResponse<?> result) throws JsonProcessingException {
        return JsonUtil.writeValueAsString(result);
    }
}
