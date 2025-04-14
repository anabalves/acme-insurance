package com.acmeinsurance.application.service;

import com.acmeinsurance.application.usecase.FindProductByIdUseCase;
import com.acmeinsurance.domain.entity.Product;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.exception.NotFoundException;
import com.acmeinsurance.domain.gateway.ProductCatalogDataGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FindProductByIdService implements FindProductByIdUseCase {

    private static final Logger log = LoggerFactory.getLogger(FindProductByIdService.class);

    private final ProductCatalogDataGateway dataGateway;

    public FindProductByIdService(ProductCatalogDataGateway dataGateway) {
        this.dataGateway = dataGateway;
    }

    @Override
    public Product execute(UUID id) {
        log.info("Fetching product with ID [{}] from catalog", id);
        try {
            Product product = dataGateway.getProduct(id);

            if (!product.active()) {
                log.warn("Product with ID [{}] is inactive", id);
                throw new BusinessException("Product is inactive");
            }

            log.info("Product with ID [{}] successfully retrieved and is active", id);
            return product;
        } catch (BusinessException ex) {
            if (ex.getStatusCode() == 404) {
                log.warn("Product with ID [{}] not found in catalog", id);
                throw new NotFoundException("Product with ID [%s] not found".formatted(id));
            }
            log.error("Error fetching product with ID [{}]: {}", id, ex.getMessage());
            throw ex;
        }
    }

}
