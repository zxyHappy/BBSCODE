package com.bluemsun.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static Object jsonTo(String json,Class c) throws IOException {
        return objectMapper.readValue(json,c);
    }


}
