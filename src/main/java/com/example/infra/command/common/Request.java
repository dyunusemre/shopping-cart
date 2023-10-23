package com.example.infra.command.common;

import com.example.infra.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public record Request(String command, String payload) {

    public static Request fromEvent(String event) throws JsonProcessingException {
        var json = JsonUtil.readTree(event);
        return new Request(json.get("command").asText(), JsonUtil.writeValueAsString(json.get("payload")));
    }
}
