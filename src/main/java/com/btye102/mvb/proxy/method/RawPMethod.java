package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

import java.lang.invoke.MethodHandle;

/**
 * @author: rd13
 * @since: 2024/12/26
 **/
public class RawPMethod implements PMethod {

    private final MethodHandle methodHandle;

    public RawPMethod(MethodHandle methodHandle) {
        this.methodHandle = methodHandle;
    }

    @Override
    public Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable {
        return methodHandle.bindTo(proxy).invokeWithArguments(args);
    }
}
