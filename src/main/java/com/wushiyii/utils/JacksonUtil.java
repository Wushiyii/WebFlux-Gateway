package com.wushiyii.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

@Slf4j
public class JacksonUtil {
 
    private static final ObjectMapper objectMapper = new ObjectMapper();
 
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    } 
 
    /** 
     * 将 Java 对象转为 JSON 字符串 
     */ 
    public static <T> String toJSON(T obj) { 
        String jsonStr; 
        try { 
            jsonStr = objectMapper.writeValueAsString(obj); 
        } catch (Exception e) { 
            log.error("Java to JSON occur error！object={}", obj, e);
            throw new RuntimeException(e); 
        } 
        return jsonStr; 
    } 
 
    /** 
     * 将 JSON 字符串转为 Java 对象 
     */ 
    public static <T> T fromJSON(String json, Class<T> type) { 
        T obj; 
        try { 
            obj = objectMapper.readValue(json, type); 
        } catch (Exception e) { 
            log.error("JSON to Java occur error！json={}, type={}", json, type, e);
            throw new RuntimeException(e); 
        } 
        return obj; 
    } 
}