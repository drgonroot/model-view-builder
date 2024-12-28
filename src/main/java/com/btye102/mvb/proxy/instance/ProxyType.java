package com.btye102.mvb.proxy.instance;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.BuildViewReflectException;
import com.btye102.mvb.proxy.method.RawPMethod;

import java.lang.reflect.Method;

/**
 * 代理类型
 * @author: rd13
 * @since: 2024/12/28
 **/
public enum ProxyType implements IProxy {
    JDK(new JdkProxy()),
    CGLIB(new CglibProxy()),
    ;

    private final IProxy proxy;

    ProxyType(IProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public <T> void checkViewClass(Class<T> viewClass) throws BuildViewReflectException {
        this.proxy.checkViewClass(viewClass);
    }

    @Override
    public void checkViewMethod(Method viewMethod) throws BuildViewReflectException {
        this.proxy.checkViewMethod(viewMethod);
    }

    @Override
    public <T> RawPMethod createRawPMethod(ProxyType proxyType, Class<T> viewClass, Method viewMethod) throws BuildViewReflectException {
        return this.proxy.createRawPMethod(proxyType, viewClass, viewMethod);
    }

    @Override
    public <T> T createProxyInstance(Class<T> viewClass, Object model, BuildContext context) throws BuildViewExecuteException {
        return this.proxy.createProxyInstance(viewClass, model, context);
    }
}
