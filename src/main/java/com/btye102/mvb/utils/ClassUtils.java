package com.btye102.mvb.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * class工具类
 *
 * @author: rd13
 * @since: 2024/12/26
 **/
public final class ClassUtils {

    /**
     * 获取方法签名
     * eg: getUserId(java.lang.Integer)
     * */
    public static String getMethodParamName(Method method) {
        String name = method.getName();
        String paramJoin = Stream.of(method.getParameterTypes()).map(Class::getName).collect(Collectors.joining(","));
        return name + "(" + paramJoin + ")";
    }

    /**
     * 获取类的方法
     * */
    public static List<Method> getMethodList(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredMethods()).collect(Collectors.toList());
    }

    public static Method getMethod(Class<?> clazz) {
        return null;
    }
}
