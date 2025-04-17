package com.acmeinsurance.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonUtils {

    private static final String LOCATION_PATTERN = "integration/%s/%s.json";
    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    public static <T> T getObjectFromFile(String folder, String file, Class<T> clazz) {
        String json = getJsonStringFromFile(folder, file);
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON to object: " + clazz.getSimpleName(), e);
        }
    }

    public static String getJsonStringFromFile(String folder, String file) {
        String fileLocation = String.format(LOCATION_PATTERN, folder, file);
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileLocation)) {
            if (stream == null) {
                throw new RuntimeException("File not found: " + fileLocation);
            }
            byte[] jsonData = StreamUtils.copyToByteArray(stream);
            return new String(jsonData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error reading JSON file: " + fileLocation, e);
        }
    }

    public static Map<String, Object> asMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to Map", e);
        }
    }

    public static boolean compareJsonIgnoringFields(String actualJson, String expectedJson, String... ignoredFields) {
        try {
            JsonNode actual = OBJECT_MAPPER.readTree(actualJson);
            JsonNode expected = OBJECT_MAPPER.readTree(expectedJson);

            for (String field : ignoredFields) {
                removeFieldRecursive(actual, field);
                removeFieldRecursive(expected, field);
            }

            return actual.equals(expected);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error comparing JSON", e);
        }
    }

    private static void removeFieldRecursive(JsonNode node, String field) {
        if (node == null)
            return;

        if (node.isObject()) {
            ((ObjectNode) node).remove(field);
            node.fields().forEachRemaining(entry -> removeFieldRecursive(entry.getValue(), field));
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                removeFieldRecursive(item, field);
            }
        }
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}
