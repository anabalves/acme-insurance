package com.acmeinsurance.unit.infrastructure.integration.adapter;

import com.acmeinsurance.config.CatalogCacheProperties;
import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.infrastructure.integration.adapter.OfferCatalogDataProvider;
import com.acmeinsurance.infrastructure.integration.dto.response.OfferCatalogResponse;
import com.acmeinsurance.infrastructure.integration.feign.CatalogOfferClient;
import com.acmeinsurance.infrastructure.integration.mapper.OfferCatalogMapper;
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
class OfferCatalogDataProviderTest {

    @Mock
    private CatalogOfferClient client;

    @Mock
    private CatalogCacheProperties cacheProperties;

    @Mock
    private OfferCatalogMapper mapper;

    @Mock
    private com.acmeinsurance.application.cache.CacheService<OfferCatalogResponse> cache;

    @InjectMocks
    private OfferCatalogDataProvider dataProvider;

    private UUID offerId;
    private OfferCatalogResponse catalogResponse;
    private Offer domainOffer;

    @BeforeEach
    void setUp() {
        offerId = QuotationTestFactory.validOfferId();
        catalogResponse = QuotationTestFactory.validOfferCatalogResponse();
        domainOffer = QuotationTestFactory.validOffer();
    }

    @Test
    void shouldReturnOfferFromCache() {
        when(cache.get("catalog:offer:" + offerId)).thenReturn(catalogResponse);
        when(mapper.toDomain(catalogResponse)).thenReturn(domainOffer);

        Offer result = dataProvider.getOffer(offerId);

        assertThat(result).isEqualTo(domainOffer);
        verify(client, never()).findById(any());
    }

    @Test
    void shouldCallCatalogAndCacheWhenOfferNotFoundInCache() {
        when(cache.get("catalog:offer:" + offerId)).thenReturn(null);
        when(client.findById(offerId)).thenReturn(catalogResponse);
        when(mapper.toDomain(catalogResponse)).thenReturn(domainOffer);
        when(cacheProperties.getCatalogOffer()).thenReturn(300L);

        Offer result = dataProvider.getOffer(offerId);

        assertThat(result).isEqualTo(domainOffer);
        verify(client).findById(offerId);
        verify(cache).put("catalog:offer:" + offerId, catalogResponse, 300L);
    }
}
