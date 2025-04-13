package com.acmeinsurance.infrastructure.messaging.kafka;

import com.acmeinsurance.application.messaging.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher<T> implements EventPublisher<T> {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, T> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, T message) {
        log.info("Publishing message to topic [{}]: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }

}
