package com.vbenadmin.backend.commoncore.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisUtils {

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedisUtils(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    // ============================ String ============================

    // 普通字符串 set
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // 普通字符串 set + 过期时间
    public void set(String key, String value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 获取字符串
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /** 原子自增（登录失败次数、计数器等） */
    public Long increment(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    // ============================ JSON ============================
    /** 存对象（JSON） */
    public <T> void setJson(String key, T value, long timeout, TimeUnit unit) {
        try {
            String json = objectMapper.writeValueAsString(value);
            stringRedisTemplate.opsForValue().set(key, json, timeout, unit);
        } catch (Exception e) {
            throw new RuntimeException("Redis JSON 序列化失败", e);
        }
    }

    /** 取对象（JSON） */
    public <T> T getJson(String key, Class<T> clazz) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json == null) {
                return null;
            }
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Redis JSON 反序列化失败", e);
        }
    }

    /** 取 List（JSON） */
    public <T> List<T> getJsonList(String key, Class<T> clazz) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json == null) {
                return Collections.emptyList();
            }
            JavaType type = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Redis JSON List 反序列化失败", e);
        }
    }

    // ============================ Set ============================
    public void sAdd(String key, Collection<String> values) {
        stringRedisTemplate.opsForSet()
                .add(key, values.toArray(new String[0]));
    }

    public Set<String> sMembers(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    public boolean sIsMember(String key, String value) {
        return Boolean.TRUE.equals(
                stringRedisTemplate.opsForSet().isMember(key, value));
    }

    // ============================ Hash（全部 String） ============================
    public void hSet(String key, String field, String value) {
        stringRedisTemplate.opsForHash().put(key, field, value);
    }

    public String hGet(String key, String field) {
        Object val = stringRedisTemplate.opsForHash().get(key, field);
        return val == null ? null : val.toString();
    }

    // ============================ Key 操作 ============================
    public boolean delete(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public void expire(String key, long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

}
