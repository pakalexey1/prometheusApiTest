package com.prometheus.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

import java.io.InputStream;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

        public static <T> T fromResource(String resourcePath, Class<T> clazz){
            try (InputStream is = Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResourceAsStream(resourcePath)){
                if(is == null) throw new IllegalArgumentException("Resource not found: " + resourcePath);
                return MAPPER.readValue(is, clazz);
            } catch (Exception e) {
                throw new RuntimeException("Failed to load JSON: " + resourcePath, e);
            }
        }

        public static String toJson(Object obj){
            try{
                return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            }catch (JsonProcessingException e){
                throw new RuntimeException("Failed to serialize object to JSON" + obj, e);
            }
        }
    }

