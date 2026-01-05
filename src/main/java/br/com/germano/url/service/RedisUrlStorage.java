package br.com.germano.url.service;

import br.com.germano.url.util.UrlStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Profile({"dev", "prod"})
public class RedisUrlStorage implements UrlStorage {

    private final RedisTemplate<String, String> redisTemplate;
    private final long ttl;

    public RedisUrlStorage(
            RedisTemplate<String, String> redisTemplate,
            @Value("${app.cache.ttl}") long ttl) {
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
    }

    @Override
    public void save(String shortCode, String originalUrl) {
        redisTemplate.opsForValue()
                .set(buildKey(shortCode), originalUrl, ttl, TimeUnit.SECONDS);
    }

    @Override
    public String find(String shortCode) {
        return redisTemplate.opsForValue()
                .get(buildKey(shortCode));
    }

    @Override
    public void delete(String shortCode) {
        redisTemplate.delete(buildKey(shortCode));
    }

    private String buildKey(String shortCode) {
        return "url:" + shortCode;
    }
}

