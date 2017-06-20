package com.jzwy.zkx.core.reflect;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MetaClassReflector
 */
public class MetaClassReflector {

    private static final ConcurrentHashMap<Class<?>, MetaClassReflector> metaClassReflectorHashMap = new ConcurrentHashMap<>();

    private Class<?> type;
    private Field[] fields;

    public MetaClassReflector(Class<?> type) {
        this.type = type;
    }

    public Field[] getFields() {
        return fields;
    }



    public static MetaClassReflector forClass(Class<?> clazz) {
        MetaClassReflector cached = metaClassReflectorHashMap.get(clazz);
        if (cached == null) {
            cached = new MetaClassReflector(clazz);
            metaClassReflectorHashMap.put(clazz, cached);
        }
        return cached;
    }
}
