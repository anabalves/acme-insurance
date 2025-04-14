package com.acmeinsurance.application.usecase;

import com.acmeinsurance.domain.entity.Offer;

import java.util.UUID;

public interface FindOfferByIdUseCase {

    Offer execute(UUID id);

}
