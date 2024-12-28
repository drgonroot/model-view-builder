package com.btye102.mvb.proxy.instance;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.BuildViewReflectException;
import com.btye102.mvb.proxy.method.RawPMethod;

import java.lang.reflect.Method;
/**
 * 代理接口
 *
 * @author: rd13
 * @since: 2024/12/28
 **/
public interface IProxy {

    /**
     * 检验视图是否支持
     * */
    default <T> void checkViewClass(Class<T> viewClass) throws BuildViewReflectException {}

    /**
     * 校验视图方法是否支持
     * */
    default void checkViewMethod(Method viewMethod) throws BuildViewReflectException {}

    /**
     * 创建对应视图原生方法
     * */
    <T> RawPMethod createRawPMethod(ProxyType proxyType, Class<T> viewClass, Method viewMethod) throws BuildViewReflectException;

    /**
     * 创建运行代理实例
     * */
     <T> T createProxyInstance(Class<T> viewClass, Object model, BuildContext context) throws BuildViewExecuteException;
}
