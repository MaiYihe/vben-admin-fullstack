package com.vbenadmin.backend.commoncore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbenadmin.backend.commoncore.utils.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean // 注册为 Spring Bean；RedisUtils 的参数（依赖）：依赖递归解析
    public RedisUtils redisUtils(StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        return new RedisUtils(stringRedisTemplate, objectMapper);
    }
}
