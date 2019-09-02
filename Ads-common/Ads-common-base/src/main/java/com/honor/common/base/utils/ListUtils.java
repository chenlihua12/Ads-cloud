package com.honor.common.base.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <V> List<V> add(V v) {
        List<V> list = new ArrayList<>();
        list.add(v);
        return list;
    }

    public static <V> List<V> add(List<V> list, V v) {
        list.add(v);
        return list;
    }

    public static <V> List<V> addAll(List<V> list1, List<V> list2) {
        list1.addAll(list2);
        return list1;
    }
}
