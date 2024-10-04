package com.backend.sahmeto.authentication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisSender {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSender.class);

    public void saveData(String key, Object data) {
        redisTemplate.opsForValue().set(key, data.toString());
        LOGGER.info("the data has been stored is {}", data);
    }
}
