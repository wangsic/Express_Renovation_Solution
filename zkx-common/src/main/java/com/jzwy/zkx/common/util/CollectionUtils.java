package com.jzwy.zkx.common.util;

import java.util.*;

/**
 * 集合util
 */
public class CollectionUtils {

    public static <T> boolean isEmpty(T[] array) {
        return null == array || 0 >= array.length;
    }

    public static <T> boolean isEmpty(List<T> list) {
        return null == list || 0 >= list.size();
    }

    public static <T> boolean isEmpty(Set<T> set) {
        return null == set || 0 >= set.size();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return null == map || 0 >= map.size();
    }

    /**
     * 数组转化为list
     *
     * @param array
     * @return
     * @throws Exception
     */
    public static <T> List<T> arrayToList(T[] array) throws Exception {
        List<T> list = new ArrayList<T>();
        if (null == array || 0 >= array.length) {
            return list;
        }
        for (T t : array) {
            list.add(t);
        }
        return list;
    }

    /**
     * 数组转化为set
     *
     * @param array
     * @return
     * @throws Exception
     */
    public static <T> Set<T> arrayToSet(T[] array) {
        Set<T> set = new HashSet<T>();
        if (null == array || 0 >= array.length) {
            return set;
        }
        for (T t : array) {
            set.add(t);
        }
        return set;
    }

    /**
     * set转化为list
     *
     * @param set
     * @return
     */
    public static <T> List<T> setToList(Set<T> set) {
        if (null == set || 0 >= set.size()) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<T>();
        list.addAll(set);
        return list;
    }

    /**
     * set转化为list
     *
     * @param list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Set<T> listToSet(List<T> list) {
        if (null == list || 0 >= list.size()) {
            return Collections.EMPTY_SET;
        }
        Set<T> set = new HashSet<T>();
        set.addAll(list);
        return set;
    }

    public static <T> List<T> collectionToList(Collection<T> collection) throws Exception {
        List<T> list = new ArrayList<T>();
        if (null == collection || 0 >= collection.size()) {
            return list;
        }
        for (T t : collection) {
            list.add(t);
        }
        return list;
    }

    /**
     * orginList中的元素全部加到targetList中，如果其中有null元素 ， 则忽略
     *
     * @param orginList
     * @param targetList
     */
    public static <T> void addAllTrimNull(List<T> orginList, List<T> targetList) {
        if (null == orginList || 0 >= orginList.size() || null == targetList) {
            return;
        }
        for (T t : orginList) {
            if (null != t) {
                targetList.add(t);
            }
        }
    }

    /**
     * orginList中的元素全部加到targetList中，如果其中有null元素 ， 则忽略
     *
     * @param orginList
     * @param targetList
     */
    public static <T> void copyListNotNull(List<T> orginList, List<T> targetList) {
        if (null == orginList || 0 >= orginList.size() || null == targetList) {
            return;
        }
        for (T t : orginList) {
            if (null != t) {
                targetList.add(t);
            }
        }
    }

    /**
     * 得到orginList中的所有非空元素的List集合的浅拷贝
     *
     * @param orginList
     * @param orginList
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getNotNullCopy(List<T> orginList) {
        if (null == orginList || 0 >= orginList.size()) {
            return Collections.EMPTY_LIST;
        }
        List<T> targetList = new ArrayList<T>();
        for (T t : orginList) {
            if (null != t) {
                targetList.add(t);
            }
        }
        return targetList;
    }
}
