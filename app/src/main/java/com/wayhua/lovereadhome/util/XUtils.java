package com.wayhua.lovereadhome.util;

import java.util.List;

/**
 * Created by 黄卫华(wayhua@126.com) on 2016/5/3.
 */
public class XUtils {
    public static String combine(List<String> list) {
        return combine(list, ",");
    }

    public static String combine(List<String> list, String split) {
        if (list == null || 0 == list.size()) {
            return "";
        }
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            String item = list.get(i);
            sb.append(item);
            if (i < list.size() - 1) {
                sb.append(split);
            }
        }
        return sb.toString();
    }

}
