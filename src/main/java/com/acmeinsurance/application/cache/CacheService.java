package com.acmeinsurance.application.cache;

public interface CacheService<T> {

    T get(String key);
    void put(String key, T value, Long ttlSeconds);
    void evict(String key);

}
