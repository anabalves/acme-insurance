package com.acmeinsurance.application.messaging;

public interface EventPublisher<T> {

    void publish(T event);

}
