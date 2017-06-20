package com.jzwy.zkx.common.encrypt;

import java.security.GeneralSecurityException;

/**
 * 进行安全加密抛出的异常
 */
public class SecurityEncoderException extends GeneralSecurityException {

    private static final long serialVersionUID = 1005L;

    public SecurityEncoderException() {
        super();
    }

    public SecurityEncoderException(String msg) {
        super(msg);
    }

    public SecurityEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityEncoderException(Throwable cause) {
        super(cause);
    }
}
