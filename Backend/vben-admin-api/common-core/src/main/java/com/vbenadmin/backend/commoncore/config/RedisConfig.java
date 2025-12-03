package com.vbenadmin.backend.commoncore.config;

import com.vbenadmin.backend.commoncore.utils.RedisUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean // 注册为 Spring Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }
}