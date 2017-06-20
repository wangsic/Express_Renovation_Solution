package com.jzwy.zkx.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegexUtils {

    /**
     * 是否是数字, 支持正数，负数和小数
     * @param input
     * @return
     */
    public static boolean isNumeric(String input){
        Pattern pattern = Pattern.compile("^(-|\\+)?\\d+(\\.\\d+)?$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * 是否是整型
     * @param input
     * @return
     */
    public static boolean isInteger(String input){
        Pattern pattern = Pattern.compile("^-?\\\\d+$");
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
