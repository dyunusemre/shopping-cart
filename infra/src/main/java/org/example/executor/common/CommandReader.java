package org.example.executor.common;

import java.io.IOException;
import java.util.List;

public interface CommandReader {
    List<String> read() throws IOException;
}
