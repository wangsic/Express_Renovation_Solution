package com.jzwy.zkx.common.cache;

import java.util.Map;

/**
 * CacheManager
 */
public class CacheManager {

    private static Map<String, CacheProvider> staticCacheProviderMap;

    public void setCacheProviderMap(Map<String, CacheProvider> cacheProviderMap) {
        staticCacheProviderMap = cacheProviderMap;
    }

    public static CacheProvider get(String provider) {
        return staticCacheProviderMap.get(provider);
    }

}
