package com.jzwy.zkx.common.serialization;

import com.jzwy.zkx.common.util.JsonUtils;

/**
 * 对象 Json 序列化
 */
public class JsonObjectSerializer implements ObjectSerializer {
    @Override
    public <T> byte[] serialize(T object) {
        return JsonUtils.toJsonBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] input, Class<T> clazz) {
        return JsonUtils.toObject(input, clazz);
    }
}
