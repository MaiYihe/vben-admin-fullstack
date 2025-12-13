package com.vbenadmin.backend.commoncore.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbenadmin.backend.commoncore.utils.RedisUtils;

@Configuration
public class RedisConfig {

    @Bean // 注册为 Spring Bean；RedisUtils 的参数（依赖）：依赖递归解析
    public RedisUtils redisUtils(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        return new RedisUtils(stringRedisTemplate, objectMapper);
    }
}
