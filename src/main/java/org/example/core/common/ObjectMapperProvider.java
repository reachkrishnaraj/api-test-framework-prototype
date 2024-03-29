package org.example.core.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperProvider {

    private static ObjectMapper objectMapper;

    private ObjectMapperProvider() {
    }

    public static ObjectMapper get() {
        if (objectMapper == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.findAndRegisterModules();
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT); // Example configuration
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Example configuratio
        }
        return objectMapper;
    }

    public static String writeValueAsString(Object value) {
        try {
            return get().writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String content, Class<T> valueType) {
        try {
            return get().readValue(content, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
