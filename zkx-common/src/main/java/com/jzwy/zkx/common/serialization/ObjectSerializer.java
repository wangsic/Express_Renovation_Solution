package com.jzwy.zkx.common.serialization;

/**
 * 对象序列化器接口
 */
public interface ObjectSerializer {

    <T> byte[] serialize(T object);

    <T> T deserialize(byte[] input, Class<T> clazz);
}