package com.jzwy.zkx.core.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 异常防护
 */
public class Guard {

    public static void notNull(Object argumentValue, String argumentName) {
        if (argumentValue == null) {
            throw new ArgumentNullException(argumentName);
        }
    }

    public static void notNullOrEmpty(String argumentValue, String argumentName) {
        if (StringUtils.isEmpty(argumentValue)) {
            throw new ArgumentNullException(argumentName);
        }
    }

    public static void isTrue(boolean condition) {
        if (!condition) {
            fail(null);
        }
    }

    public static void isTrue(boolean condition, String message) {
        if (!condition) {
            fail(message);
        }
    }

    public static void fail(String message) {
        throw new RuntimeException(message);
    }
}