package com.jzwy.zkx.common.cache;

/**
 * 抽象的缓存提供器
 */
public interface CacheProvider {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    void set(String cacheName, String key, Object value);

    /**
     * 设置带超时时间的缓存, 单位秒
     *
     * @param key
     * @param value
     * @param expireSeconds
     */
    void set(String cacheName, String key, Object value, Integer expireSeconds);

    /**
     * 得到缓存
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T get(String cacheName, String key, Class<T> clazz);

    /**
     * 得到缓存
     *
     * @param key
     * @return
     */
    Object get(String cacheName, String key);

    /**
     * 移除缓存
     *
     * @param key
     */
    void remove(String cacheName, String key);

    /**
     * 判断缓存是否存在
     *
     * @param key
     * @return
     */
    boolean exists(String cacheName, String key);

}
