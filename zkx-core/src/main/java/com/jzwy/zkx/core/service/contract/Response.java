package com.jzwy.zkx.core.service.contract;

import com.jzwy.zkx.core.util.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * 抽象的请求响应
 */
public class Response<T> {

    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取列表数据
     *
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * 设置数据
     *
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    public Boolean isSuccessful() {
        return message != null && Constants.RESPONSE_SUCCESS_CODE.equals(code);
    }

    public static <T> Response<T> initializedResult() {
        Response response = new Response();
        response.setCode(Constants.RESPONSE_SUCCESS_CODE);
        return response;
    }

    public static Response<Void> writeSuccess() {
        return writeSuccess(null, null);
    }

    public static Response<Void> writeSuccess(String messageText) {
        return writeSuccess(messageText, null);
    }

    public static <T> Response<T> writeSuccess(T data) {
        return writeSuccess(null, data);
    }

    public static <T> Response<T> writeSuccess(String messageText, T data) {
        Response<T> response = new Response<>();
        response.setCode(Constants.RESPONSE_SUCCESS_CODE);
        response.setMessage(StringUtils.isEmpty(messageText) ? Constants.RESPONSE_SUCCESS_COMMON_MESSAGE : messageText);
        response.setData(data);
        return response;
    }

    public static Response<Void> writeError(String messageText) {
        return writeError(null, messageText);
    }

    public static Response<Void> writeError(String messageCode, String messageText) {
        Response<Void> response = Response.initializedResult();
        response.setCode(StringUtils.isEmpty(messageCode) ? Constants.RESPONSE_FAILURE_COMMON_CODE : messageCode);
        response.setMessage(StringUtils.isEmpty(messageText) ? Constants.RESPONSE_FAILURE_COMMON_MESSAGE : messageText);
        return response;
    }
}
