package com.jzwy.zkx.common.codec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

/**
 * UTF-8 编码解码
 */
public class Utf8 {

    private static final Charset CHARSET = Charset.forName("UTF-8");

    /**
     * 得到字符的UTF编码字节数组
     */
    public static byte[] encode(CharSequence string) {
        try {
            ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
            byte[] bytesCopy = new byte[bytes.limit()];
            System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());

            return bytesCopy;
        }
        catch (CharacterCodingException e) {
            throw new IllegalArgumentException("编码异常", e);
        }
    }

    /**
     * 将UTF-8编码的字节数组解码成字符串
     */
    public static String decode(byte[] bytes) {
        try {
            return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        }
        catch (CharacterCodingException e) {
            throw new IllegalArgumentException("解码异常", e);
        }
    }

}
