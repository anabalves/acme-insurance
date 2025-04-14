package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.FindOfferByIdUseCase;
import com.acmeinsurance.domain.entity.Offer;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.gateway.OfferCatalogDataGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindOfferByIdService implements FindOfferByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(FindOfferByIdService.class);

    private final OfferCatalogDataGateway dataGateway;

    public FindOfferByIdService(OfferCatalogDataGateway dataGateway) {
        this.dataGateway = dataGateway;
    }

    @Override
    public Offer execute(UUID id) {
        log.info("Fetching offer with ID [{}] from catalog", id);
        try {
            Offer offer = dataGateway.getOffer(id);

            if (!offer.active()) {
                log.warn("Offer with ID [{}] is inactive", id);
                throw new BusinessException("Offer is inactive");
            }

            log.info("Offer with ID [{}] successfully retrieved and is active", id);
            return offer;
        } catch (BusinessException ex) {
            if (ex.getStatusCode() == 404) {
                log.warn("Offer with ID [{}] not found in catalog", id);
                throw new NotFoundException("Offer with ID [%s] not found".formatted(id));
            }
            log.error("Error fetching offer with ID [{}]: {}", id, ex.getMessage());
            throw ex;
        }
    }

}
