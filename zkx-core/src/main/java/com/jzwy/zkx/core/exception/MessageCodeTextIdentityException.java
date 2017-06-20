package com.jzwy.zkx.core.exception;

/**
 * 消息编号和文本标识的异常
 */
public class MessageCodeTextIdentityException extends RuntimeException {

    private static final long serialVersionUID = -1408496956808219908L;

    private String messageCode;
    private String messageText;

    public MessageCodeTextIdentityException(String messageCode, String messageText) {
        this.messageCode = messageCode;
        this.messageText = messageText;
    }

    @Override
    public String getMessage() {
        return this.messageText;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public String getMessageText() {
        return this.messageText;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getCanonicalName() + "[");
        builder.append("code='").append(this.messageCode).append('\'');
        builder.append(", message='").append(this.messageText).append('\'');
        builder.append(']');
        return builder.toString();
    }
}
