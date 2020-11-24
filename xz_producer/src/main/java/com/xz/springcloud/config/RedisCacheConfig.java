package com.xz.springcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author xz
 * @ClassName RedisCacheConfig
 * @Description
 * @date 2020/3/21 0021 14:04
 **/
@Configuration
public class RedisCacheConfig {

    @Autowired
    RedisConnectionFactory factory;

    /*@Bean
    public CacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = RedisCacheManager.create(factory);
        return redisCacheManager;
    }*/
}
