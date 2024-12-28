package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;

import java.lang.reflect.Method;

/**
 * 代理方法执行 ProxyMethod
 *
 * @author: rd13
 * @since: 2024/12/26
 **/
public interface PMethod {

    /**
     * @param viewClass view类
     * @param proxy 生成代理对象
     * @param modelInProxy cglib 使用
     * @param model model数据 jdk proxy 使用
     */
    <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException;
}