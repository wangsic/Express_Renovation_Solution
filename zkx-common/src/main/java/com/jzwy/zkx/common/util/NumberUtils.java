package com.jzwy.zkx.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NumberUtils
 */
public class NumberUtils {

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isPositiveInteger(String orginal, boolean includingDecimal) {
        if (StringUtils.isEmpty(orginal)) {
            return false;
        }

        if (isPositiveInteger(orginal)) {
            return true;
        }

        boolean isDecimal = org.apache.commons.lang3.math.NumberUtils.isNumber(orginal);
        if (!isDecimal || (isDecimal && !includingDecimal)) {
            return false;
        }

        int pointIndex = orginal.indexOf(".");
        return StringUtils.containsOnly(orginal.substring(pointIndex + 1), "0");
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    public static boolean isPercentage(String orginal) {
        return isMatch("^\\d+\\.?\\d*\\%$", orginal);
    }

    public static BigDecimal toDecimalFromPercentage(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }

        String newValue = value.replace("%", "");
        if (isRealNumber(newValue)) {
            return new BigDecimal(newValue).multiply(BigDecimal.valueOf(0.01));
        }
        return null;
    }

    public static BigDecimal nvl(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        return value;
    }

    public static Integer nvl(Integer value) {
        if (value == null) {
            return 0;
        }
        return value;
    }


    public static void main(String[] args) {

        String s = "UserDO";

        String clazz = s.substring(0, s.length() - 2);

        BigDecimal s1 = new BigDecimal(15.00);
        BigDecimal s2 = new BigDecimal(25);

        int co = s1.compareTo(s2);

        boolean sb = s1.equals(s2);

        ThreadLocal<Long> t = new ThreadLocal<>();

        Set<Integer> hashList = new HashSet<>();
        Set<Long> vList = new HashSet<>();
        for (Long i = 0L; i < 5L; i++) {
            t.set(i);
            Integer c = t.hashCode();
            hashList.add(c);
            Long l = t.get();
            vList.add(l);
        }

        BigDecimal d3 = new BigDecimal(1.000);
        BigDecimal d4 = new BigDecimal(1L);

        boolean db = d3.equals(d4);

        boolean b1 = org.apache.commons.lang3.math.NumberUtils.isNumber("0.0");
        boolean b2 = org.apache.commons.lang3.math.NumberUtils.isNumber("100.01");
        boolean b3 = org.apache.commons.lang3.math.NumberUtils.isNumber("100.0");
        boolean b4 = org.apache.commons.lang3.math.NumberUtils.isNumber("100.10");
        boolean b5 = org.apache.commons.lang3.math.NumberUtils.isNumber("0.1");
        boolean b6 = org.apache.commons.lang3.math.NumberUtils.isNumber("200");
        boolean b7 = org.apache.commons.lang3.math.NumberUtils.isNumber("-200");
        boolean b8 = org.apache.commons.lang3.math.NumberUtils.isNumber("-200.19");

        boolean b9 = NumberUtils.isPercentage("10%");
        boolean b10 = NumberUtils.isPercentage("10.29%");
        boolean b11 = NumberUtils.isPercentage("10.02%");
        boolean b12 = NumberUtils.isPercentage("10.00%");
        boolean b13 = NumberUtils.isPercentage("0.1%");
        boolean b14 = NumberUtils.isPercentage("0.10%");
        boolean b15 = NumberUtils.isPercentage("0.01%");
        boolean b16 = NumberUtils.isPercentage("0.12%");
        boolean b17 = NumberUtils.isPercentage("0.00%");
        boolean b18 = NumberUtils.isPercentage("10");

        /**
         boolean b1 = NumberUtils.isPositiveInteger("0.0", true);
         boolean b2 = NumberUtils.isPositiveInteger("100.01", true);
         boolean b3 = NumberUtils.isPositiveInteger("100.0", true);
         boolean b4 = NumberUtils.isPositiveInteger("100.10", true);
         boolean b5 = NumberUtils.isPositiveInteger("0.1", true);
         boolean b6 = NumberUtils.isPositiveInteger("200", true);
         */
        int v = Double.valueOf("100").intValue();
        int v1 = Double.valueOf("100.0").intValue();

        BigDecimal d1 = NumberUtils.toDecimalFromPercentage("21.19%");
        BigDecimal d2 = NumberUtils.toDecimalFromPercentage("10%");
    }

}
