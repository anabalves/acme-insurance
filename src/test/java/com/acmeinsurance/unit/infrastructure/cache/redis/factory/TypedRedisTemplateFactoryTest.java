package com.acmeinsurance.unit.infrastructure.cache.redis.factory;

import com.acmeinsurance.infrastructure.cache.redis.factory.TypedRedisTemplateFactory;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class TypedRedisTemplateFactoryTest {

    @Test
    void shouldCreateTypedRedisTemplateSuccessfully() {
        // given
        RedisConnectionFactory connectionFactory = mock(RedisConnectionFactory.class);

        // when
        RedisTemplate<String, OfferCatalogResponse> template = TypedRedisTemplateFactory
                .create(OfferCatalogResponse.class, connectionFactory);

        // then
        assertThat(template).isNotNull();
        assertThat(template.getConnectionFactory()).isEqualTo(connectionFactory);
        assertThat(template.getKeySerializer()).isNotNull();
        assertThat(template.getValueSerializer()).isNotNull();
    }
}
