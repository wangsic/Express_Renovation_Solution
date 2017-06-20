package com.jzwy.zkx.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 对于value的处理util
 */
public class ValueUtils {

    public static JSONObject getNotNull(JSONObject value) {
        return null == value ? new JSONObject() : value;
    }

    public static JSONArray getNotNull(JSONArray value) {
        return null == value ? new JSONArray() : value;
    }

    public static String getNotNull(String value) {
        return null == value ? "" : value.trim();
    }

    public static String getNotNull(String value, String defaultValue) {
        if (null == defaultValue) {
            defaultValue = StringUtils.EMPTY;
        }
        return null == value ? defaultValue : value;
    }

    public static Integer getNotNull(Integer value, Integer defaultValue) {
        if (null == defaultValue) {
            defaultValue = 0;
        }
        return null == value ? defaultValue : value;
    }

    public static Integer getNotNull(Integer value) {
        return null == value ? 0 : value;
    }

    public static Long getNotNull(Long value, Long defaultValue) {
        if (null == defaultValue) {
            defaultValue = 0l;
        }
        return null == value ? defaultValue : value;
    }

    public static Long getNotNull(Long value) {
        return null == value ? 0 : value;
    }

    public static Double getNotNull(Double value, Double defaultValue) {
        if (null == defaultValue) {
            defaultValue = 0d;
        }
        return null == value ? defaultValue : value;
    }

    public static Double getNotNull(Double value) {
        return null == value ? 0d : value;
    }

    public static Float getNotNull(Float value, Float defaultValue) {
        if (null == defaultValue) {
            defaultValue = 0f;
        }
        return null == value ? defaultValue : value;
    }

    public static Float getNotNull(Float value) {
        return null == value ? 0f : value;
    }

    /**
     * Translate integer to '是' or '否'
     *
     * @param value
     * @return '是' when and only when value == 1
     */
    public static String intToChineseYesNo(Integer value) {
        if (value == null)
            return "否";
        if (value == 1)
            return "是";
        return "否";
    }

}
