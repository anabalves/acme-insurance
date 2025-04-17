package com.acmeinsurance.unit.infrastructure.messaging.kafka;

import com.acmeinsurance.config.KafkaProperties;
import com.acmeinsurance.infrastructure.messaging.dto.event.QuotationCreatedEvent;
import com.acmeinsurance.infrastructure.messaging.kafka.KafkaQuotationCreatedPublisher;
import com.acmeinsurance.infrastructure.messaging.mapper.QuotationCreatedEventMapper;
import com.acmeinsurance.util.QuotationTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaQuotationCreatedPublisherTest {

    @Mock
    private KafkaTemplate<String, QuotationCreatedEvent> kafkaTemplate;

    @Mock
    private KafkaProperties kafkaProperties;

    @Mock
    private KafkaProperties.Topics topics;

    @Mock
    private QuotationCreatedEventMapper mapper;

    @InjectMocks
    private KafkaQuotationCreatedPublisher publisher;

    @Test
    void shouldPublishQuotationCreatedEventToKafka() {
        // given
        var quotation = QuotationTestFactory.validQuotation();
        var event = QuotationTestFactory.validQuotationCreatedEvent();

        when(mapper.toEvent(quotation)).thenReturn(event);
        when(kafkaProperties.getTopics()).thenReturn(topics);
        when(topics.getQuotationCreated()).thenReturn("quotation-created-topic");

        // when
        publisher.publish(quotation);

        // then
        verify(mapper).toEvent(quotation);
        verify(kafkaTemplate).send("quotation-created-topic", event);
    }
}
