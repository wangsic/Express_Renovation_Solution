package com.jzwy.zkx.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Json操作工具类
 */
public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.getSerializationConfig()
                .with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.getDeserializationConfig()
                .with(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 将对象转换为Json字符串
     *
     * @param obj 待转换对象
     * @return 转换后的Json字符串
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("将对象转换为Json字符串时出现异常[object=" + obj + "]", e);
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    public static byte[] toJsonBytes(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException("将对象转换为Json字节数组时出现异常[object=" + obj + "]", e);
        }
    }

    /**
     * 将Json字符串转换为对象
     *
     * @param <T>        转换后的对象类型
     * @param jsonString 待转换Json字符串
     * @param objClazz   要转换为的对象类型
     * @return 转换后的对象
     */
    public static <T> T toObject(String jsonString, Class<T> objClazz) {
        try {
            return objectMapper.readValue(jsonString, objClazz);
        } catch (Exception e) {
            throw new RuntimeException("将Json字符串转换为对象时出现异常[str=" + jsonString + ";objectType=" + objClazz + "]", e);
        }
    }

    /**
     *
     * @param bytes
     * @param objClazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(byte[] bytes, Class<T> objClazz) {
        try {
            return objectMapper.readValue(bytes, objClazz);
        } catch (Exception e) {
            throw new RuntimeException("将Json字节数组转换为对象时出现异常[objectType=" + objClazz + "]", e);
        }
    }

    /**
     * 将Json字符串转换为对象列表
     *
     * @param <T>           转换后的对象类型
     * @param str           待转换Json字符串
     * @param typeReference 转换类型
     * @return 转换后的对象列表
     */
    public static <T> List<T> toObjectList(String str, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("将Json字符串转换为对象列表时出现异常[str=" + str + ";typeReference=" + typeReference + "]", e);
        }
    }

    /**
     * 将对象转换成Json Hash Map.
     *
     * @param object
     * @return
     */
    public static Map<String, Object> toJsonMap(Object object) {
        JSONObject jsonObj = (JSONObject) JSON.toJSON(object);
        return jsonObj;
    }
}
