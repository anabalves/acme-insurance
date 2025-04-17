package com.acmeinsurance.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class KafkaConsumerTestHelper {

    private final KafkaConsumer<String, String> consumer;

    public KafkaConsumerTestHelper(String bootstrapServers, String topic, String groupId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", groupId);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public List<String> consumeMessages(Duration timeout) {
        ConsumerRecords<String, String> records = consumer.poll(timeout);
        return records.records(records.partitions().iterator().next()).stream().map(ConsumerRecord::value)
                .collect(Collectors.toList());
    }

    public void close() {
        consumer.close();
    }
}
