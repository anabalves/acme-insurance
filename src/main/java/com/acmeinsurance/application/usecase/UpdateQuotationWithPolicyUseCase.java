package com.acmeinsurance.application.usecase;

import com.acmeinsurance.domain.entity.PolicyIssued;

public interface UpdateQuotationWithPolicyUseCase {

    void execute(PolicyIssued policyIssued);

}
