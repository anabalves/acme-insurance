package com.acmeinsurance.unit.infrastructure.cache.redis;

import com.acmeinsurance.infrastructure.cache.redis.RedisCacheService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RedisCacheServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisCacheService<String> cacheService;

    @Test
    void shouldReturnValueFromCache() {
        // given
        String key = "myKey";
        String value = "myValue";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(value);

        // when
        String result = cacheService.get(key);

        // then
        assertThat(result).isEqualTo(value);
        verify(redisTemplate.opsForValue()).get(key);
    }

    @Test
    void shouldPutValueInCacheWithTTL() {
        // given
        String key = "myKey";
        String value = "myValue";
        long ttl = 120L;
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // when
        cacheService.put(key, value, ttl);

        // then
        verify(redisTemplate.opsForValue()).set(key, value, ttl, TimeUnit.SECONDS);
    }
}
