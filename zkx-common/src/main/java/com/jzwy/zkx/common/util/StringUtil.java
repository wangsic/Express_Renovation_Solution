package com.jzwy.zkx.common.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 字符串处理
 *
 * @author lilin
 */
public class StringUtil {

    /**
     * @param ch
     * @return
     */
    public static boolean isNumberChar(char ch) {
        return '0' <= ch && ch <= '9';
    }

    /**
     * @param ch
     * @return
     */
    public static boolean isEnglishChar(char ch) {
        return isUpperCaseChar(ch) || isLowerCaseChar(ch);
    }

    /**
     * @param ch
     * @return
     */
    public static boolean isUpperCaseChar(char ch) {
        return 'A' <= ch && ch <= 'Z';
    }

    /**
     * @param ch
     * @return
     */
    public static boolean isLowerCaseChar(char ch) {
        return 'a' <= ch && ch <= 'z';
    }

    /**
     * 将文本切成单词和符号
     */
    @SuppressWarnings("unchecked")
    public static List<String> split(List<String> strList, String separator, boolean saveSeparator) {
        if (CollectionUtils.isEmpty(strList)) {
            return Collections.EMPTY_LIST;
        }
        List<String> itemList = new ArrayList<>();
        for (String itemStr : strList) {
            if (null == itemStr || StringUtils.EMPTY.equals(itemStr)) {
                continue;
            }
            itemList.addAll(split(itemStr, separator, saveSeparator));
        }
        return itemList;
    }

    /**
     * 将文本切成单词和符号
     */
    @SuppressWarnings("unchecked")
    public static List<String> split(String str, String separator, boolean saveSeparator) {
        if (StringUtil.isEmpty(str)) {
            return Collections.EMPTY_LIST;
        }
        List<String> itemList = new ArrayList<>();
        char separatorFirtChar = separator.charAt(0);
        int preSegmentStartIndex = 0;
        int i = 0;
        for (; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == separatorFirtChar) {
                // boolean isMatched = true;
                int j = 1;
                for (; j < separator.length() && i + j < str.length(); j++) {
                    if (separator.charAt(j) != str.charAt(i + j)) {
                        break;
                    }
                }
                if (j >= separator.length()) {
                    if (preSegmentStartIndex < i) {
                        itemList.add(str.substring(preSegmentStartIndex, i));
                    }
                    if (saveSeparator) {
                        itemList.add(separator);
                    }

                    preSegmentStartIndex = i + j;
                    i = i + j - 1;
                }
            }
        }
        if (preSegmentStartIndex < str.length()) {
            itemList.add(str.substring(preSegmentStartIndex, str.length()));
        }
        return itemList;
    }

    /**
     * 将sourceStr中所有匹配上anchorStr得字符串anchorStr的部分都替换成replaceStr
     *
     * @param sourceStr
     * @param anchorStr
     * @param replaceStr
     * @return
     */
    public static String replaceAll(String sourceStr, String anchorStr, String replaceStr) {
        if (null == sourceStr || null == anchorStr || null == replaceStr) {
            return sourceStr;
        }
        if (anchorStr.length() > sourceStr.length()) {
            return sourceStr;
        }
        char anchorFirtChar = anchorStr.charAt(0);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sourceStr.length(); i++) {
            char ch = sourceStr.charAt(i);
            if (ch != anchorFirtChar) {
                builder.append(ch);
                continue;
            }
            boolean isMatched = true;
            int j = i;
            for (int k = 0; k < anchorStr.length(); k++) {
                j = i + k;
                if (j >= sourceStr.length() || sourceStr.charAt(j) != anchorStr.charAt(k)) {
                    isMatched = false;
                    continue;
                }
            }
            if (isMatched) {
                builder.append(replaceStr);
                i = j;
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    public static String nullParser(String str) {
        return str == null ? "" : str;
    }

    public static String toLowerCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.toLowerCase();
    }

    public static String toTrimAndLowerCase(String str) {
        if (null == str) {
            return null;
        }
        return str.trim().toLowerCase();
    }

    public static boolean equals(String str1, String str2) {
        if (null == str1 || null == str2) {
            return false;
        }
        return str1.equals(str2);
    }

    /**
     * 判断是不是手机号
     *
     * @param keyword
     * @return
     */
    public static boolean isMobileNumber(String keyword) {
        return null != keyword && keyword.matches("^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    }

    /**
     * @param str
     * @param separator
     * @return
     * @throws Exception
     */
    public static Pair<Long, Long> spiltToLongRange(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        str = trim(str);
        Pair<Long, Long> pair = new Pair<>();
        if (null == separator) {
            pair.setKey(Long.parseLong(str));
            return pair;
        }
        String[] splitArray = str.trim().split(separator);
        if (0 >= splitArray.length || splitArray.length > 2) {
            throw new Exception("str is illegal, splitArray.length=" + splitArray.length + " , str=" + str);
        }
        if (2 == splitArray.length) {
            if (!StringUtil.isEmpty(splitArray[0])) {
                pair.setKey(Long.parseLong(splitArray[0]));
            }
            if (!StringUtil.isEmpty(splitArray[1])) {
                pair.setValue(Long.parseLong(splitArray[1]));
            }
        }
        if (str.startsWith(separator) && !StringUtil.isEmpty(splitArray[1])) {
            pair.setValue(Long.parseLong(splitArray[1]));
        }
        if (str.endsWith(separator) && !StringUtil.isEmpty(splitArray[0])) {
            pair.setKey(Long.parseLong(splitArray[0]));
        }
        return pair;
    }

    public static Pair<Integer, Integer> spiltToIntRange(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        str = trim(str);
        Pair<Integer, Integer> pair = new Pair<>();
        if (null == separator) {
            pair.setKey(Integer.parseInt(str));
            return pair;
        }
        String[] splitArray = str.trim().split(separator);
        if (0 >= splitArray.length || splitArray.length > 2) {
            throw new Exception("str is illegal, splitArray.length=" + splitArray.length + " , str=" + str);
        }
        if (2 == splitArray.length) {
            if (!StringUtil.isEmpty(splitArray[0])) {
                pair.setKey(Integer.parseInt(splitArray[0]));
            }
            if (!StringUtil.isEmpty(splitArray[1])) {
                pair.setValue(Integer.parseInt(splitArray[1]));
            }
        }
        if (str.startsWith(separator) && !StringUtil.isEmpty(splitArray[1])) {
            pair.setValue(Integer.parseInt(splitArray[1]));
        }
        if (str.endsWith(separator) && !StringUtil.isEmpty(splitArray[0])) {
            pair.setKey(Integer.parseInt(splitArray[0]));
        }
        return pair;
    }

    public static Pair<Double, Double> spiltToDoubleRange(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        str = trim(str);
        Pair<Double, Double> pair = new Pair<>();
        if (null == separator) {
            pair.setKey(Double.parseDouble(str));
            return pair;
        }
        String[] splitArray = str.trim().split(separator);
        if (0 >= splitArray.length || splitArray.length > 2) {
            throw new Exception("str is illegal, splitArray.length=" + splitArray.length + " , str=" + str);
        }
        if (2 == splitArray.length) {
            if (!StringUtil.isEmpty(splitArray[0])) {
                pair.setKey(Double.parseDouble(splitArray[0]));
            }
            if (!StringUtil.isEmpty(splitArray[1])) {
                pair.setValue(Double.parseDouble(splitArray[1]));
            }
        } else {
            if (str.startsWith(separator) && !StringUtil.isEmpty(splitArray[1])) {
                pair.setValue(Double.parseDouble(splitArray[1]));
            }
            if (str.endsWith(separator) && !StringUtil.isEmpty(splitArray[0])) {
                pair.setKey(Double.parseDouble(splitArray[0]));
            }
        }
        return pair;
    }

    /**
     * 把字符串切分成字符串列表
     *
     * @param str
     * @param separator
     * @return
     * @throws Exception
     */
    public static List<String> spiltToString(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        List<String> l = new ArrayList<>();
        if (null == separator) {
            l.add(str);
            return l;
        }
        String[] splitArray = str.trim().split(separator);
        for (String subStr : splitArray)
            l.add(subStr);
        return l;
    }

    /**
     * 把字符串切分成字符串列表
     *
     * @param str
     * @param separator
     * @return
     * @throws Exception
     */
    public static JSONArray spiltToStringJSONArray(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        JSONArray l = new JSONArray();
        if (null == separator) {
            l.add(str);
            return l;
        }
        String[] splitArray = str.trim().split(separator);
        for (String subStr : splitArray)
            l.add(subStr);
        return l;
    }

    public static String[] spiltToStringArray(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        String[] splitArray = str.trim().split(separator);
        return splitArray;
    }

    public static List<Long> spiltToLong(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        List<Long> l = new ArrayList<>();
        if (null == separator) {
            l.add(Long.parseLong(str));
            return l;
        }
        String[] splitArray = str.trim().split(separator);
        for (String numStr : splitArray) {
            l.add(Long.parseLong(trim(numStr)));
        }
        return l;
    }

    public static List<Integer> spiltToInteger(String str, String separator) throws Exception {
        if (isEmpty(str)) {
            throw new Exception("str is empty.");
        }
        List<Integer> l = new ArrayList<>();
        if (null == separator) {
            l.add(Integer.parseInt(str));
            return l;
        }
        String[] splitArray = str.trim().split(separator);
        for (String numStr : splitArray) {
            if (isEmpty(numStr)) {
                continue;
            }
            l.add(Integer.parseInt(numStr));
        }
        return l;
    }

    public static List<Double> spiltToDouble(String str, String separator) throws Exception {
        if (null == str) {
            throw new Exception("str is null.");
        }
        List<Double> l = new ArrayList<>();
        if (null == separator) {
            l.add(Double.parseDouble(str));
            return l;
        }
        String[] splitArray = str.trim().split(separator);
        for (String numStr : splitArray)
            l.add(Double.parseDouble(numStr));
        return l;
    }

    public static boolean isEmpty(String string) {
        if (null == string || StringUtils.EMPTY.equals(string.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 切割类似"price:asc,sell:desc"这样的字符串成为map
     *
     * @param str
     * @param mainSeparator 类似上面的“,”
     * @param subSeparator  类似上面的“:”
     * @return
     */
    public static Map<String, String> splitToMap(String str, String mainSeparator, String subSeparator)
            throws Exception {
        if (isEmpty(str)) {
            return new HashMap<>();
        }
        String[] strArray = str.trim().split(mainSeparator);
        Map<String, String> map = new HashMap<>(strArray.length);

        for (String kv : strArray) {
            String[] kvArray = kv.trim().split(subSeparator);
            if (kvArray.length != 2 || isEmpty(kvArray[0]) || isEmpty(kvArray[1])) {
                throw new Exception("the format of str is error, error: \"" + kv + "\" in " + str);
            }
            map.put(kvArray[0], kvArray[1]);
        }
        return map;
    }

    public static String toListStatement(String pre, List<?> list, String splitTag, String end) {
        if (null == list || 0 >= list.size() || null == splitTag) {
            return null;
        }
        pre = (null == pre) ? "" : pre;
        end = (null == end) ? "" : end;

        StringBuilder builder = new StringBuilder();
        builder.append(pre);
        boolean isFirst = true;

        for (Object obj : list) {
            if (isFirst) {
                isFirst = false;
            } else {
                builder.append(splitTag);
            }
            builder.append(obj);
        }
        builder.append(end);
        return builder.toString();
    }

    public static String toSetStatement(String pre, Set<?> set, String splitTag, String end) {
        if (null == set || 0 >= set.size() || null == splitTag) {
            return null;
        }
        pre = (null == pre) ? "" : pre;
        end = (null == end) ? "" : end;

        StringBuilder builder = new StringBuilder();
        builder.append(pre);
        boolean isFirst = true;

        for (Object obj : set) {
            if (isFirst) {
                isFirst = false;
            } else {
                builder.append(splitTag);
            }
            builder.append(obj);
        }
        builder.append(end);
        return builder.toString();
    }

    // 将str转为list
    public static List<String> toList(String str, String splitChar) {
        if (isEmpty(str)) {
            return null;
        }
        String[] array = str.split(splitChar);
        List<String> list = new ArrayList<>(array.length);
        list.addAll(Arrays.asList(array));
        return list;
    }

    // 将a-b转为[a b]
    public static String toRangeStr(String str, String splitChar) {
        if (isEmpty(str)) {
            return null;
        }
        String[] array = str.split(splitChar);
        return "[" + array[0] + " " + array[1] + "]";
    }

    public static String toRangeStr(Object val1, String splitChar, Object val2) {
        if (null == val1 || null == val2) {
            return null;
        }
        String str1 = null == val1 ? "" : val1 + "";
        String str2 = null == val2 ? "" : val2 + "";
        return str1 + splitChar + str2;
    }


    public static String join(String splitStr, JSONArray jsonArray) {
        if (null == splitStr || null == jsonArray) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < jsonArray.size(); i++) {
            String obj = jsonArray.getString(i);
            if (null == obj)
                continue;
            if (!first) {
                builder.append(splitStr);
            } else {
                first = false;
            }
            builder.append(obj);
        }
        return builder.toString();
    }

    public static String join(String splitStr, String... strArray) {
        if (null == splitStr || null == strArray) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String obj : strArray) {
            if (null == obj)
                continue;
            if (!first) {
                builder.append(splitStr);
            } else {
                first = false;
            }
            builder.append(obj);
        }
        return builder.toString();
    }

    public static String join(String splitStr, List<String> stringList) {
        if (null == splitStr || null == stringList) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String obj : stringList) {
            if (null == obj)
                continue;
            if (!first) {
                builder.append(splitStr);
            } else {
                first = false;
            }
            builder.append(obj);
        }
        return builder.toString();
    }

    /**
     * 将类似“0001010011001”中为1的索引存到int型的数组 中
     *
     * @param str
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static List<Integer> strIndexToIntList(String str, char indexStr) throws Exception {
        if (null == str) {
            return Collections.EMPTY_LIST;
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            if (indexStr == str.charAt(i)) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * 后期将一些其他的字符做为trim的空字符列表中
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (null == str)
            return null;
        char[] tags = {'\n', '\t'};
        str = str.trim();
        int startIndex = 0;
        int endIndex = str.length();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!inCharSet(tags, ch)) {
                startIndex = i;
                break;
            }
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            char ch = str.charAt(i);
            if (!inCharSet(tags, ch)) {
                endIndex = i + 1;
                break;
            }
        }
        return str.substring(startIndex, endIndex);
    }

    private static boolean inCharSet(char[] array, char ch) {
        for (char item : array) {
            if (item == ch) {
                return true;
            }
        }
        return false;
    }

    public static String trim(String str, String tag) {
        if (null == str)
            return null;
        if (str.equals(tag)) {
            return "";
        }
        int indexStart = 0;
        int indexEnd = str.length();
        if (str.startsWith(tag)) {
            indexStart = tag.length();
        }
        if (str.endsWith(tag)) {
            indexEnd = str.length() - tag.length();
        }
        if (indexEnd >= indexStart) {
            str = str.substring(indexStart, indexEnd);
        }
        return str;
    }

    public static String limit(String str, int length) {
        return str.length() <= length ? str : str.substring(0, length - 1);
    }

    public static String nvl(String str, String value) {
        if (StringUtils.isEmpty(str)) {
            return value;
        }
        return str;
    }

    public static String parseBlank(String str) {
        if (StringUtils.isBlank(str)) return null;
        return str;
    }

    public static int compare(String str, String anotherStr) {
        if (StringUtils.isEmpty(anotherStr) && StringUtils.isEmpty(str)) {
            return 0;
        }
        if (StringUtils.isEmpty(anotherStr) && StringUtils.isNotEmpty(str)) {
            return 1;
        }
        return str.compareTo(anotherStr);
    }

    public static boolean startWith(String str, String startTag) {
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(startTag) || startTag.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < startTag.length(); i++) {
            if (startTag.charAt(i) != str.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static String toString(Object value) {
        return toString(value, null);
    }

    public static String toString(Object value, String defaultValueIfNull) {
        if (value == null) {
            return defaultValueIfNull;
        }
        return String.valueOf(value);
    }

}
