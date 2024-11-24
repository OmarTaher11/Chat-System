package com.instabug.backend.challenge.redis.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, Long> redisTemplate;

    public RedisService(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public long getNextNumber(String... keyAttributes) {
        return this.redisTemplate.opsForValue().increment(this.formatKey(keyAttributes));
    }

    public void setNextNumber(Long value, String... keyAttributes) {
        this.redisTemplate.opsForValue().set(this.formatKey(keyAttributes), value);
    }

    public Long getCurrentValue(String... keyAttributes) {
        Long value = this.redisTemplate.opsForValue().get(this.formatKey(keyAttributes));

        return value != null ? value : 0L;
    }

    private String formatKey(String... attr) {
        StringBuilder formattedString = new StringBuilder("Attributes: ");
        for (String key : attr) {
            formattedString.append(key).append(":");
        }
        return formattedString.toString();
    }
}
