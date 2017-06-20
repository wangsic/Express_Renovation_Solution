package com.jzwy.zkx.common.encrypt;

/**
 * 安全加密接口
 */
public interface SecurityEncoder {

    String CHARSET_UTF8 = "UTF-8";

    String encrypt(String rawInput) throws SecurityEncoderException;

    boolean matches(String rawInput, String encryptedInput);
}
