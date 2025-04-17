package com.acmeinsurance.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerTestHelper {

    private final KafkaProducer<String, String> producer;

    public KafkaProducerTestHelper(String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.producer = new KafkaProducer<>(props);
    }

    public void sendJson(String topic, Object payload) {
        try {
            String json = JsonUtils.getObjectMapper().writeValueAsString(payload);
            producer.send(new ProducerRecord<>(topic, json));
            producer.flush();
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish message", e);
        }
    }

    public void close() {
        producer.close();
    }
}
