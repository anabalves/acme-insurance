package com.acmeinsurance.unit.infrastructure.web.mapper;

import com.acmeinsurance.domain.entity.Customer;
import com.acmeinsurance.infrastructure.web.dto.request.CustomerRequest;
import com.acmeinsurance.infrastructure.web.dto.response.CustomerResponse;
import com.acmeinsurance.infrastructure.web.mapper.CustomerMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void shouldMapCustomerRequestToDomain() {
        // given
        CustomerRequest request = QuotationTestFactory.validCustomerRequest();

        // when
        Customer result = mapper.toDomain(request);

        // then
        assertThat(result.documentNumber()).isEqualTo("36205578900");
        assertThat(result.name()).isEqualTo("John Wick");
        assertThat(result.email()).isEqualTo("johnwick@gmail.com");
        assertThat(result.type().name()).isEqualTo("NATURAL");
        assertThat(result.gender().name()).isEqualTo("MALE");
        assertThat(result.dateOfBirth()).isEqualTo("1973-05-02");
        assertThat(result.phoneNumber()).isEqualTo(11950503030L);
    }

    @Test
    void shouldMapCustomerRequestToDomainWhenTypeAndGenderAreNull() {
        // given
        CustomerRequest request = QuotationTestFactory.customerRequestWithNullTypeAndGender();

        // when
        Customer result = mapper.toDomain(request);

        // then
        assertThat(result.documentNumber()).isEqualTo("36205578900");
        assertThat(result.name()).isEqualTo("John Wick");
        assertThat(result.email()).isEqualTo("johnwick@gmail.com");
        assertThat(result.type()).isNull();
        assertThat(result.gender()).isNull();
        assertThat(result.dateOfBirth()).isEqualTo("1973-05-02");
        assertThat(result.phoneNumber()).isEqualTo(11950503030L);
    }

    @Test
    void shouldMapCustomerToCustomerResponse() {
        // given
        Customer customer = QuotationTestFactory.validCustomer();

        // when
        CustomerResponse response = mapper.toDto(customer);

        // then
        assertThat(response.documentNumber()).isEqualTo("36205578900");
        assertThat(response.name()).isEqualTo("John Wick");
        assertThat(response.email()).isEqualTo("johnwick@gmail.com");
        assertThat(response.type()).isEqualTo("NATURAL");
        assertThat(response.gender()).isEqualTo("MALE");
        assertThat(response.dateOfBirth()).isEqualTo("1973-05-02");
        assertThat(response.phoneNumber()).isEqualTo(11950503030L);
    }

    @Test
    void shouldMapCustomerToCustomerResponseWhenTypeAndGenderAreNull() {
        // given
        Customer customer = QuotationTestFactory.customerWithNullTypeAndGender();

        // when
        CustomerResponse response = mapper.toDto(customer);

        // then
        assertThat(response.documentNumber()).isEqualTo("36205578900");
        assertThat(response.name()).isEqualTo("John Wick");
        assertThat(response.email()).isEqualTo("johnwick@gmail.com");
        assertThat(response.type()).isNull();
        assertThat(response.gender()).isNull();
        assertThat(response.dateOfBirth()).isEqualTo("1973-05-02");
        assertThat(response.phoneNumber()).isEqualTo(11950503030L);
    }

    @Test
    void shouldReturnNullWhenCustomerRequestIsNull() {
        // when
        Customer result = mapper.toDomain(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullWhenCustomerIsNull() {
        // when
        CustomerResponse result = mapper.toDto(null);

        // then
        assertThat(result).isNull();
    }
}
