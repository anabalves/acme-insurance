package com.acmeinsurance.unit.infrastructure.integration.adapter;

import com.acmeinsurance.config.CatalogCacheProperties;
import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.infrastructure.integration.adapter.ProductCatalogDataProvider;
import com.acmeinsurance.infrastructure.integration.dto.response.ProductCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.CatalogProductClient;
import com.acmeinsurance.infrastructure.integration.mapper.ProductCatalogMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCatalogDataProviderTest {

    @Mock
    private CatalogProductClient client;

    @Mock
    private CatalogCacheProperties cacheProperties;

    @Mock
    private ProductCatalogMapper mapper;

    @Mock
    private com.acmeinsurance.application.cache.CacheService<ProductCatalogResponse> cache;

    @InjectMocks
    private ProductCatalogDataProvider dataProvider;

    private UUID productId;
    private ProductCatalogResponse response;
    private Product product;

    @BeforeEach
    void setUp() {
        productId = QuotationTestFactory.validProductId();
        response = QuotationTestFactory.validProductCatalogResponse();
        product = QuotationTestFactory.validProduct();
    }

    @Test
    void shouldReturnProductFromCache() {
        when(cache.get("catalog:product:" + productId)).thenReturn(response);
        when(mapper.toDomain(response)).thenReturn(product);

        Product result = dataProvider.getProduct(productId);

        assertThat(result).isEqualTo(product);
        verify(client, never()).findById(any());
    }

    @Test
    void shouldCallCatalogAndCacheWhenProductNotFoundInCache() {
        when(cache.get("catalog:product:" + productId)).thenReturn(null);
        when(client.findById(productId)).thenReturn(response);
        when(mapper.toDomain(response)).thenReturn(product);
        when(cacheProperties.getCatalogProduct()).thenReturn(300L);

        Product result = dataProvider.getProduct(productId);

        assertThat(result).isEqualTo(product);
        verify(client).findById(productId);
        verify(cache).put("catalog:product:" + productId, response, 300L);
    }
}
