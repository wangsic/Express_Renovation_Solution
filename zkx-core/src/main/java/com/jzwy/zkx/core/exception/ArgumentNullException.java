package com.jzwy.zkx.core.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数为空的异常
 */
public class ArgumentNullException extends RuntimeException {

    private static final long serialVersionUID = -4441413063102486943L;

    private static final String exceptionMessage = "值不能为null. 参数名:%s. %s";

    public ArgumentNullException(String argument) {
        this(argument, StringUtils.EMPTY);
    }

    public ArgumentNullException(String argument, String message) {
        super(String.format(exceptionMessage, argument, message).trim());
    }
}