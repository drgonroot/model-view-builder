package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.PMethodExecutionException;
import com.btye102.mvb.exception.ProxyTypeCheckException;
import com.btye102.mvb.proxy.instance.ProxyType;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

/**
 * @author: rd13
 * @since: 2024/12/26
 **/
public class RawPMethod implements PMethod {

    private final ProxyType proxyType;
    private final MethodHandle methodHandle;

    public RawPMethod(ProxyType proxyType, MethodHandle methodHandle) {
        this.proxyType = proxyType;
        this.methodHandle = methodHandle;
    }

    @Override
    public <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException {
        try {
            switch (proxyType) {
                case JDK:
                    return methodHandle.bindTo(proxy).invokeWithArguments(args);
                case CGLIB:
                    return ((MethodProxy)proxy).invokeSuper(modelInProxy, args);
                default:
                    throw new ProxyTypeCheckException(String.format("构建视图%s类失败，未支持%s", viewClass.getName(), proxyType.name()));
            }
        } catch (Throwable e) {
            throw new PMethodExecutionException(String.format("%s视图类执行%s方法异常", viewClass.getName(), method.getName()), e);
        }

    }
}
