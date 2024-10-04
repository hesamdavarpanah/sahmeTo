package com.backend.sahmeto.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

@Service
public class RedisReceiver {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Object getData (String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
