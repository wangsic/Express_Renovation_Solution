package com.jzwy.zkx.common.cache.local;


import com.jzwy.zkx.common.cache.CacheProvider;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本地缓存
 */
public class LocalCacheProvider implements CacheProvider {

    private final CacheManager cacheManager;

    private static final Lock syncLock = new ReentrantLock();

    public LocalCacheProvider() {
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
    }

    @Override
    public void set(String cacheName, String key, Object value) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            try {
                syncLock.lock();
                if (cache == null) {
                    // cacheConfiguration 堆缓存100
                    CacheConfiguration<String, Object> cacheConfiguration = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(1000))
                            .build();
                    cache = cacheManager.createCache(cacheName, cacheConfiguration);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                syncLock.unlock();
            }
        }
        cache.put(key, value);
    }

    @Override
    public void set(String cacheName, String key, Object value, Integer expireSeconds) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            try {
                syncLock.lock();
                if (cache == null) {
                    // cacheConfiguration 堆缓存100
                    CacheConfiguration<String, Object> cacheConfiguration = CacheConfigurationBuilder
                            .newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(1000))
                            .withExpiry(Expirations.timeToLiveExpiration(Duration.of(expireSeconds, TimeUnit.SECONDS)))
                            .build();
                    cache = cacheManager.createCache(cacheName, cacheConfiguration);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                syncLock.unlock();
            }
        }
        cache.put(key, value);
    }

    @Override
    public <T> T get(String cacheName, String key, Class<T> clazz) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            return null;
        }
        return (T) cache.get(key);
    }

    @Override
    public Object get(String cacheName, String key) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            return null;
        }
        return cache.get(key);
    }

    @Override
    public void remove(String cacheName, String key) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            return;
        }
        cache.remove(key);
    }

    @Override
    public boolean exists(String cacheName, String key) {
        Cache<String, Object> cache = cacheManager.getCache(cacheName, String.class, Object.class);
        if (cache == null) {
            return false;
        }
        return cache.containsKey(key);
    }
}
