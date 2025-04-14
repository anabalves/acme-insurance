package com.acmeinsurance.infrastructure.messaging.kafka;

import com.acmeinsurance.application.messaging.QuotationCreatedPublisher;
import com.acmeinsurance.config.KafkaProperties;
import com.acmeinsurance.domain.entity.Quotation;
import com.acmeinsurance.infrastructure.messaging.dto.event.QuotationCreatedEvent;
import com.acmeinsurance.infrastructure.messaging.mapper.QuotationCreatedEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaQuotationCreatedPublisher implements QuotationCreatedPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaQuotationCreatedPublisher.class);

    private final KafkaTemplate<String, QuotationCreatedEvent> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    private final QuotationCreatedEventMapper mapper;

    public KafkaQuotationCreatedPublisher(KafkaTemplate<String, QuotationCreatedEvent> kafkaTemplate,
            KafkaProperties kafkaProperties, QuotationCreatedEventMapper mapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
        this.mapper = mapper;
    }

    @Override
    public void publish(Quotation quotation) {
        var event = mapper.toEvent(quotation);
        var topic = kafkaProperties.getTopics().getQuotationCreated();
        log.info("Publishing quotation created event to topic [{}]: {}", topic, event);
        kafkaTemplate.send(topic, event);
    }

}
