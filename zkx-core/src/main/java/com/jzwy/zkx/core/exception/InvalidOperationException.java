package com.jzwy.zkx.core.exception;

/**
 * 无效操作的异常
 */
public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = -2766997219571396931L;

    public InvalidOperationException() {
        super();
    }

    public InvalidOperationException(String msg) {
        super(msg);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }
}
