package com.jzwy.zkx.common.serialization;

/**
 *  对象 XML 序列化
 */
public class XmlObjectSerializer implements ObjectSerializer {
    @Override
    public <T> byte[] serialize(T object) {
        return null;
    }

    @Override
    public <T> T deserialize(byte[] input, Class<T> clazz) {
        return null;
    }
}
