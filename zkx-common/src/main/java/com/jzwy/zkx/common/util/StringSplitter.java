package com.jzwy.zkx.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringSplitter {

    private List<String> stringList = new ArrayList<>();

    private StringSplitter() {
    }

    public StringSplitter split(String str, String separator, boolean saveSeparator) {
        if (null == stringList) {
            stringList = StringUtil.split(str, separator, saveSeparator);
            return this;
        }
        stringList.add(str);
        stringList = StringUtil.split(stringList, separator, saveSeparator);
        return this;
    }

    public StringSplitter split(List<String> strList, String separator, boolean saveSeparator) {
        if (null == stringList) {
            stringList = StringUtil.split(strList, separator, saveSeparator);
            return this;
        }
        stringList.addAll(strList);
        stringList = StringUtil.split(stringList, separator, saveSeparator);
        return this;
    }

    public StringSplitter split(String separator, boolean saveSeparator) {
        if (null == stringList) {
            return this;
        }
        stringList = StringUtil.split(stringList, separator, saveSeparator);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<String> toList() {
        return null == stringList ? Collections.EMPTY_LIST : stringList;
    }

    public static StringSplitter create(String str, String separator, boolean saveSeparator) {
        StringSplitter splitter = new StringSplitter();
        return splitter.split(str, separator, saveSeparator);
    }

    public static void main(String[] args) {
        String sourceStr = ";;;2_3;;;;;_22_2_;_;_23;;;";
        System.out.println(sourceStr);
        System.out.println("---------");
        List<String> list = StringSplitter.create(sourceStr, ";", true).split("_", true).toList();
        for (String item : list) {
            System.out.println(item);
        }
    }

}
