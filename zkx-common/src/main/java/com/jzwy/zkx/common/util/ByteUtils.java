package com.jzwy.zkx.common.util;

/**
 *  字节操作工具类
 */
public class ByteUtils {

    public static String ToHexString(byte[] bytes) {
        StringBuilder hexBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hexBuilder.append("0");
            }
            hexBuilder.append(hex.toUpperCase());
        }
        return hexBuilder.toString();
    }

}
