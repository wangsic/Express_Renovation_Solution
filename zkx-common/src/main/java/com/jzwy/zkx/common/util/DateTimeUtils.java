package com.jzwy.zkx.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间操作工具类
 */
public class DateTimeUtils {

    public final static String TIMEFORMAT_MMDDYYYY = "MM/dd/yyyy";
    public final static String TIMEFORMAT_MMDDYYYYHHMMSS = "MM/dd/yyyy HH:mm:ss";

    public final static String TIMEFORMAT_STANDARD = "yyyy/MM/dd HH:mm:ss";
    public final static String TIMEFORMAT_STANDARD_LONG = "yyyy/MM/dd HH:mm:ss.fff";
    public final static String TIMEFORMAT_STANDARD_SHORT = "yyyy/MM/dd HH:mm";

    public final static String TIMEFORMAT_DDMMMYYYYHHMM = "dd MMM yyyy HH:mm";
    public final static String TIMEFORMAT_MMYYYY = "MM/yyyy";


    public static String toDateTimeString(Date date, String format){
        return toDateTimeString(date, format, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static String toDateTimeString(Date date, String format, Locale locale){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        return dateFormat.format(date);
    }

    public static Date toDateTime(String date, String format){
        return toDateTime(date, format, Locale.getDefault(Locale.Category.FORMAT));
    }

    public static Date toDateTime(String date, String format, Locale locale){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, locale);
        try {
            return dateFormat.parse(date);
        }
        catch (ParseException e){
            return null;
        }
    }

    public static Date toDateTime(Date date, String format){
        return toDateTime(date.toString(), format);
    }

    public static Date toDateTime(Date date, String format, Locale locale){
        return toDateTime(date.toString(), format, locale);
    }

}
