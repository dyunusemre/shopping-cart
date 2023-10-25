package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readValue(String content, Class<T> type) {
        try {
            return objectMapper.readValue(content, type);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static JsonNode readTree(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static String writeValueAsString(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
