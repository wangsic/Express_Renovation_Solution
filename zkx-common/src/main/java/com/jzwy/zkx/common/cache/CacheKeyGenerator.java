package com.jzwy.zkx.common.cache;

/**
 * 缓存键生成器
 */
public interface CacheKeyGenerator<T> {
    String generate(T obj);
}
