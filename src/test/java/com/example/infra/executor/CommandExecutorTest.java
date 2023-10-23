package com.example.infra.executor;

import com.example.infra.executor.file.FileCommandReader;
import com.example.infra.executor.file.FileCommandWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class CommandExecutorTest {
    CommandExecutor commandExecutorUnderTest;

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_read_file_and_write_to_output_file() throws IOException {
        //given
        commandExecutorUnderTest = new CommandExecutor(new FileCommandReader("src/test/resources/command.txt"), new FileCommandWriter("src/test/resources/output.txt"));
        //when
        commandExecutorUnderTest.executeCommands();
        //assert
        var outputFile = Files.readAllLines(Paths.get("src/test/resources/output.txt"));
        var expectedFile = Files.readAllLines(Paths.get("src/test/resources/expectedOutputFile.txt"));
        assertThat(outputFile).isEqualTo(expectedFile);
    }
}