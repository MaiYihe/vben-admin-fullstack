package com.vbenadmin.backend.commoncore.utils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisUtils {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public RedisUtils(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    // ============================ String ============================

    // 普通 set（无过期）
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 普通 set（无过期）
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    // 查询得到单一结果
    public <T> T get(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : clazz.cast(value);
    }

    // 查询得到 List
    public <T> List<T> getList(String key, Class<T> clazz) {
        try {
            String json = stringRedisTemplate.opsForValue().get(key);
            if (json == null) {
                return Collections.emptyList();
            }
            JavaType type = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("Redis 读取 List 反序列化失败", e);
        }
    }

    // 专门为 Token 提供的：只传秒数
    public void set(String key, Object value, long timeoutSeconds) {
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public boolean delete(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    // ============================ Hash ============================
    public void hSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Object hGet(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    // ============================ 过期时间 ============================
    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }
}
