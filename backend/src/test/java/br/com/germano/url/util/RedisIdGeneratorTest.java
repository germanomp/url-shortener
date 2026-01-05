package br.com.germano.url.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("dev")
class RedisIdGeneratorTest {

    @Autowired
    private RedisIdGenerator generator;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeEach
    void cleanUp() {
        redisTemplate.delete("url:id:sequence");
    }

    @Test
    void shouldGenerateIncrementalIds() {
        long id1 = generator.nextId();
        long id2 = generator.nextId();

        assertThat(id2).isEqualTo(id1 + 1);
    }
}