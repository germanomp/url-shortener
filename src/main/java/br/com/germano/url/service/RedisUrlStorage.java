package br.com.germano.url.service;

import br.com.germano.url.util.UrlStorage;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
public class RedisUrlStorage implements UrlStorage {

    private final StringRedisTemplate redisTemplate;

    public RedisUrlStorage(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String code, String url) {
        redisTemplate.opsForValue().set(code, url);
    }

    @Override
    public String find(String code) {
        return redisTemplate.opsForValue().get(code);
    }
}
