package br.com.germano.url.util;

import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "prod", "test"})
public class RedisIdGenerator implements IdGenerator {

    private static final String KEY = "url:id:sequence";

    private final StringRedisTemplate redisTemplate;

    public RedisIdGenerator(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long nextId() {
        return redisTemplate.opsForValue().increment(KEY);
    }
}
