package com.jzwy.zkx.common.codec;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * 编码器
 */
public class Encoder {

    /**
     * 把源字符集转成目标字符集
     *
     * @param rawInput
     * @param sourceCharset
     * @param targetCharset
     * @return
     */
    public static String rectifyEncoding(String rawInput, String sourceCharset, String targetCharset) {
        if (StringUtils.isEmpty(rawInput)) {
            return rawInput;
        }

        String newEncodedValue;
        try {
            byte[] bytes = rawInput.getBytes(sourceCharset);
            newEncodedValue = new String(bytes, targetCharset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return newEncodedValue;
    }

}
