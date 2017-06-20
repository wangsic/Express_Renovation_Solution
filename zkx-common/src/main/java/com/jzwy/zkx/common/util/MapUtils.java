package com.jzwy.zkx.common.util;

import java.util.Map;

/**
 * MapUtils
 */
public class MapUtils {

    public <K, V> V getString(Map<K, V> entry, K key, V defaultValue){
        if (entry == null){
            return defaultValue;
        }
        V value = entry.get(key);
        if (value == null){
            return defaultValue;
        }
        return value;
    }
}
