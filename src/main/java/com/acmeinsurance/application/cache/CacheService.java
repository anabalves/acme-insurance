package com.acmeinsurance.application.cache;

public interface CacheService<T> {

    T get(String key);
    void put(String key, T value);
    void evict(String key);

}
