package com.mm.weclubs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建人: fangzanpan
 * 创建时间: 16/8/5 下午5:32
 * 描述: 一些常见的普通的工具方法
 */
public class LibCommonUtils {

    /**
     * 检查字符串是否为空
     *
     * @param str   需要检查的字符串
     * @return  true:字符串为空; false:字符串不为空
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断一个数组是否为空,如果数组为null或者长度为0,返回true
     *
     * @param arrayList 需要判空的数组
     * @return  true:数组为空,否则返回false
     */
    public static boolean isEmpty(ArrayList arrayList) {
        return arrayList == null || (arrayList.size() == 0);
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断一个键值对是否为空,如果键值对等于null或者长度等于0,返回true
     *
     * @param map   需要判空的键值对
     * @return  true:键值对为空,否则返回false
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(String[] strings) {
        return strings == null || strings.length == 0;
    }

}
