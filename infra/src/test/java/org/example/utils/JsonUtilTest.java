package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilTest {

    @Test
    void should_read_value() throws JsonProcessingException {
        //given
        String payload = """
                {
                    "cartId": 1,
                    "categoryId": 100
                }""";
        //when
        var result = JsonUtil.readValue(payload, TestRecord.class);
        //then
        assertThat(result).isNotNull();
        assertThat(result.cartId()).isEqualTo(1);
        assertThat(result.categoryId()).isEqualTo(100);
    }

    @Test
    void should_read_tree() throws JsonProcessingException {
        //given
        String payload = """
                {
                    "cartId": 1,
                    "categoryId": 100
                }""";
        //when
        var result = JsonUtil.readTree(payload);
        //then
        assertThat(result).isNotNull();
        assertThat(result.get("cartId").asInt()).isEqualTo(1);
        assertThat(result.get("categoryId").asInt()).isEqualTo(100);
    }

    @Test
    void should_write_as_value_string() throws JsonProcessingException {
        //given
        var testRecord = new TestRecord(1, 100);
        //when
        var result = JsonUtil.writeValueAsString(testRecord);
        //then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("{\"cartId\":1,\"categoryId\":100}");
    }

    private record TestRecord(int cartId, int categoryId) {
    }
}