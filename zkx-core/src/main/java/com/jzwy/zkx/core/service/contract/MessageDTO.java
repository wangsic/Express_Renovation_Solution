package com.jzwy.zkx.core.service.contract;

/**
 * Response的消息传输对象
 */
public class MessageDTO {

    private String code;
    private String message;
    private String exceptionMessage;

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
}
