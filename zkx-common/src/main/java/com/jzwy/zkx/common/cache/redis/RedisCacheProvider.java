package com.jzwy.zkx.common.cache.redis;


import com.jzwy.zkx.common.cache.CacheKeyGenerator;
import com.jzwy.zkx.common.cache.CacheObjectGenerator;
import com.jzwy.zkx.common.cache.CacheProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * RedisCacheProvider
 */
public interface RedisCacheProvider extends CacheProvider {

    <T> void setList(String key, Collection<T> values);

    <T> void setList(String key, Collection<T> values, Integer expireSeconds);

    <T> Collection<T> getList(String key, Class<T> clazz);

    void setHash(String hashId, String field, String value);

    void setHash(Map<String, Map<String, String>> hashFieldValueMap);

    void setHash(String hashId, Object value);

    <T> void setHash(CacheKeyGenerator<T> keyGenerator, Collection<T> values, Class<T> clazz);

    String getHash(String hashId, String field);

    <T> List<T> getHash(String patternHashId, String[] fields, CacheObjectGenerator<T> objectGenerator, Class<T> clazz);

    Map<String, String> getHash(String hashId, String[] fields);
}

