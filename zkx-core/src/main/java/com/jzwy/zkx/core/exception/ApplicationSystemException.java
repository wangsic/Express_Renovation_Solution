package com.jzwy.zkx.core.exception;

/**
 * 应用系统异常
 */
public class ApplicationSystemException extends MessageCodeTextIdentityException {

    private static final long serialVersionUID = -7962857800188584847L;

    public ApplicationSystemException(String messageCode, String messageText) {
        super(messageCode, messageText);
    }
}
