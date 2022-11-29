package com.verify.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCacheConfig {

    public CacheManager cacheManager()
    {
        CaffeineCacheManager CacheManager = new CaffeineCacheManager("userId");
        CacheManager.setCaffeine(caffeineCacheBuilder());
        return CacheManager;
    }

     Caffeine<Object, Object> caffeineCacheBuilder() {
    return Caffeine.newBuilder()
            .initialCapacity(50)
            .maximumSize(500)
            .expireAfterAccess(60, TimeUnit.MILLISECONDS)
            .weakKeys()
            .recordStats();
    }
}
