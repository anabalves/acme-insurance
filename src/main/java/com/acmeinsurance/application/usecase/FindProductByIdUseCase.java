package com.acmeinsurance.application.usecase;

import com.acmeinsurance.domain.entity.Product;

import java.util.UUID;

public interface FindProductByIdUseCase {

    Product execute(UUID id);

}
