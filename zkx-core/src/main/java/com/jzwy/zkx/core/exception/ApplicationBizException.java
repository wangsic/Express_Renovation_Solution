package com.jzwy.zkx.core.exception;

/**
 * 应用业务异常
 */
public class ApplicationBizException extends MessageCodeTextIdentityException {

    private static final long serialVersionUID = 4171404646987455172L;

    public ApplicationBizException(String messageCode, String messageText) {
        super(messageCode, messageText);
    }
}
