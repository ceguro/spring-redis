package com.example.springredis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

    private static final String CONTROLLED_PREFIX = "myPrefix_";

    public static String getCacheKey(String relevant){
        return CONTROLLED_PREFIX + relevant;
    }

    @Cacheable(cacheNames = "myCache")
    public String simpleCacheThis() {
        log.info("Message Not from Cache");
        return "This is";
    }

    @CacheEvict(cacheNames = "myCache")
    public void forgetAboutThis(){
        log.info("Forgetting everything about this!");
    }

    // add parameter relevant to cache key and save a string if doesn't exist
    @Cacheable(cacheNames = "myCache", key = "T(com.example.springredis.service.CacheService).getCacheKey(#relevant)")
    public String cacheThis(String relevant, String unrelevantTrackingId){
        log.info("Returning NOT from cache. Tracking: {}!", unrelevantTrackingId);
        return "this is it";
    }

    @CacheEvict(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
    public void forgetAboutThis(String relevant){
        log.info("Forgetting everything about this '{}'!", relevant);
    }
}
