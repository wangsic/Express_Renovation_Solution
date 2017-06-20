package com.jzwy.zkx.common.cache;

import java.util.Map;

/**
 * 缓存对象解析器用于从缓存读取对象
 */
public interface CacheObjectGenerator<T> {
    T generate(Map<String, String> values);
}
