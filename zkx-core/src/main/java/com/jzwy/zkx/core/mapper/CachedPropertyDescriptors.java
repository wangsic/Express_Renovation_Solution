package com.jzwy.zkx.core.mapper;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * BeanInfo 的 PropertyDescriptors的缓存
 */
public class CachedPropertyDescriptors {

    private static final ConcurrentHashMap<Class<?>, CachedPropertyDescriptors> propertyDescriptorsConcurrentHashMap = new ConcurrentHashMap<>();

    private Class<?> type;
    private PropertyDescriptor[] propertyDescriptors;
    private Map<String, PropertyDescriptor> propertyDescriptorHashMap = new HashMap<>();

    public CachedPropertyDescriptors(Class<?> type) {
        this.type = type;
        this.resolvePropertyDescriptors(type);
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        return propertyDescriptors;
    }

    public PropertyDescriptor getPropertyDescriptor(String propertyName) {
        return propertyDescriptorHashMap.get(propertyName);
    }

    private void resolvePropertyDescriptors(Class<?> type) {
        propertyDescriptors = BeanUtils.getPropertyDescriptors(type);
        int propDescCount = propertyDescriptors.length;
        for (int i = 0; i < propDescCount; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            propertyDescriptorHashMap.put(propertyDescriptor.getName(), propertyDescriptor);
        }
    }

    public static CachedPropertyDescriptors forClass(Class<?> clazz) {
        CachedPropertyDescriptors cachedPropertyDescriptors = propertyDescriptorsConcurrentHashMap.get(clazz);
        if (cachedPropertyDescriptors == null) {
            cachedPropertyDescriptors = new CachedPropertyDescriptors(clazz);
            propertyDescriptorsConcurrentHashMap.put(clazz, cachedPropertyDescriptors);
        }
        return cachedPropertyDescriptors;
    }

}
