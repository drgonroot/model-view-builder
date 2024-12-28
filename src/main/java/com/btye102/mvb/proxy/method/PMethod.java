package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

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
     * @param model model数据
     */
    Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable;
}