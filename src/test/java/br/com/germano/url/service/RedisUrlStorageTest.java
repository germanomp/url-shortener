package br.com.germano.url.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RedisUrlStorageTest {

    @Autowired
    private RedisUrlStorage storage;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void shouldStoreAndRetrieveUrl() {
        storage.save("abc", "https://google.com");

        String result = storage.find("abc");

        assertThat(result).isEqualTo("https://google.com");
    }

    @Test
    void shouldExpireAfterTtl() throws InterruptedException {
        storage.save("ttl", "https://example.com");

        assertThat(storage.find("ttl")).isNotNull();

        Thread.sleep(1100);

        assertThat(storage.find("ttl")).isNull();
    }
}
