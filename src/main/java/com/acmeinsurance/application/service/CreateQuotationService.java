package com.acmeinsurance.application.service;

import com.acmeinsurance.application.messaging.QuotationCreatedPublisher;
import com.acmeinsurance.application.usecase.CreateQuotationUseCase;
import com.acmeinsurance.application.usecase.FindOfferByIdUseCase;
import com.acmeinsurance.application.usecase.FindProductByIdUseCase;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.domain.exception.BusinessException;
import com.acmeinsurance.domain.repository.QuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CreateQuotationService implements CreateQuotationUseCase {

    private static final Logger log = LoggerFactory.getLogger(CreateQuotationService.class);

    private final QuotationRepository repository;
    private final FindProductByIdUseCase findProductByIdUseCase;
    private final FindOfferByIdUseCase findOfferByIdUseCase;
    private final QuotationCreatedPublisher quotationCreatedPublisher;

    public CreateQuotationService(QuotationRepository repository, FindProductByIdUseCase findProductByIdUseCase,
            FindOfferByIdUseCase findOfferByIdUseCase, QuotationCreatedPublisher quotationCreatedPublisher) {
        this.repository = repository;
        this.findProductByIdUseCase = findProductByIdUseCase;
        this.findOfferByIdUseCase = findOfferByIdUseCase;
        this.quotationCreatedPublisher = quotationCreatedPublisher;
    }

    @Override
    public Quotation execute(Quotation quotation) {
        log.info("Creating quotation for product [{}] and offer [{}]", quotation.productId(), quotation.offerId());

        var product = findProductByIdUseCase.execute(quotation.productId());
        var offer = findOfferByIdUseCase.execute(quotation.offerId());

        if (!product.offers().contains(quotation.offerId())) {
            throw new BusinessException("Offer does not belong to the specified product");
        }

        quotation.coverages().forEach((name, value) -> {
            if (!offer.coverages().containsKey(name)) {
                throw new BusinessException("Coverage [%s] is not available in the offer".formatted(name));
            }

            var max = offer.coverages().get(name);
            if (value.compareTo(max) > 0) {
                throw new BusinessException("Coverage [%s] exceeds max allowed value".formatted(name));
            }
        });

        for (var assistance : quotation.assistances()) {
            if (!offer.assistances().contains(assistance)) {
                throw new BusinessException("Assistance [%s] is not available in the offer".formatted(assistance));
            }
        }

        var premium = quotation.totalMonthlyPremiumAmount();
        var premiumRange = offer.monthlyPremiumAmount();
        if (premium.compareTo(premiumRange.minAmount()) < 0 || premium.compareTo(premiumRange.maxAmount()) > 0) {
            throw new BusinessException("Total monthly premium amount is outside the allowed range");
        }

        var expectedCoverageTotal = quotation.coverages().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        if (quotation.totalCoverageAmount().compareTo(expectedCoverageTotal) != 0) {
            throw new BusinessException("Total coverage amount does not match the sum of individual coverages");
        }

        var now = LocalDateTime.now();
        var newQuotation = quotation.withCreatedAt(now).withUpdatedAt(now);
        var saved = repository.save(newQuotation);

        quotationCreatedPublisher.publish(saved);

        log.info("Quotation [{}] created and published successfully", saved.id());
        return saved;
    }

}
