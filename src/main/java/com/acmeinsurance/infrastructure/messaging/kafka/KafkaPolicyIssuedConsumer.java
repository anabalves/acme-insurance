package com.acmeinsurance.infrastructure.messaging.kafka;

import com.acmeinsurance.application.usecase.UpdateQuotationWithPolicyUseCase;
import com.acmeinsurance.infrastructure.messaging.dto.event.PolicyIssuedEvent;
import com.acmeinsurance.infrastructure.messaging.mapper.PolicyIssuedMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaPolicyIssuedConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaPolicyIssuedConsumer.class);

    private final UpdateQuotationWithPolicyUseCase useCase;
    private final PolicyIssuedMapper mapper;

    public KafkaPolicyIssuedConsumer(UpdateQuotationWithPolicyUseCase useCase, PolicyIssuedMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "${acme.kafka.topics.policy-issued}", groupId = "${acme.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, PolicyIssuedEvent> record) {
        log.info("Consumed event from topic [{}]: {}", record.topic(), record.value());
        var policyIssued = mapper.toDomain(record.value());
        useCase.execute(policyIssued);
    }

}
