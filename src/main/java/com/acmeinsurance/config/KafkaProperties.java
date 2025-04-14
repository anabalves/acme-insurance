package com.acmeinsurance.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "acme.kafka")
public class KafkaProperties {

    private Topics topics = new Topics();
    private Consumer consumer = new Consumer();

    public static class Topics {
        private String quotationCreated;
        private String policyIssued;

        public String getQuotationCreated() {
            return quotationCreated;
        }

        public void setQuotationCreated(String quotationCreated) {
            this.quotationCreated = quotationCreated;
        }

        public String getPolicyIssued() {
            return policyIssued;
        }

        public void setPolicyIssued(String policyIssued) {
            this.policyIssued = policyIssued;
        }
    }

    public static class Consumer {
        private String groupId;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
}
