package com.acmeinsurance.unit.infrastructure.messaging.kafka;

import com.acmeinsurance.application.usecase.UpdateQuotationWithPolicyUseCase;
import com.acmeinsurance.domain.entity.PolicyIssued;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import com.acmeinsurance.infrastructure.messaging.kafka.KafkaPolicyIssuedConsumer;
import com.acmeinsurance.infrastructure.messaging.mapper.PolicyIssuedMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaPolicyIssuedConsumerTest {

    @Mock
    private UpdateQuotationWithPolicyUseCase useCase;

    @Mock
    private PolicyIssuedMapper mapper;

    @InjectMocks
    private KafkaPolicyIssuedConsumer consumer;

    @Test
    void shouldConsumeAndProcessPolicyIssuedEvent() {
        // given
        PolicyIssuedEvent event = QuotationTestFactory.validPolicyIssuedEvent();
        PolicyIssued domainEvent = QuotationTestFactory.validPolicyIssued();

        ConsumerRecord<String, PolicyIssuedEvent> record = new ConsumerRecord<>("policy-issued-topic", 0, 0L, "key",
                event);

        when(mapper.toDomain(event)).thenReturn(domainEvent);

        // when
        consumer.listen(record);

        // then
        verify(mapper).toDomain(event);
        verify(useCase).execute(domainEvent);
    }
}
