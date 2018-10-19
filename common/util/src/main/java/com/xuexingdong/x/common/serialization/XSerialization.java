package com.xuexingdong.x.common.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.Map;

public class XSerialization {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
    }

    private static final XmlMapper XML_MAPPER;

    static {
        XML_MAPPER = new XmlMapper();
        XML_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        XML_MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
        XML_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        XML_MAPPER.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClazz, Class<V> ValueClazz) {
        try {
            return OBJECT_MAPPER.readValue(json, new TypeReference<Map<K, V>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toJSON(T object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode fromJSON(String json) {
        return fromJSON(json, JsonNode.class);
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromXML(String xml, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(xml, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> fromXML(String xml, Class<K> keyClazz, Class<V> valueClazz) {
        try {
            return XML_MAPPER.readValue(xml, new TypeReference<Map<K, V>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXML(Object object) {
        try {
            return XML_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toXML(Object object, String rootElement) {
        try {
            return XML_MAPPER.writer().withRootName(rootElement).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
