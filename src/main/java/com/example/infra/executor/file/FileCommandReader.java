package com.example.infra.executor.file;

import com.example.infra.executor.common.CommandReader;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
public class FileCommandReader implements CommandReader {
    private final String filePath;

    @Override
    public List<String> read() {
        try {
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
