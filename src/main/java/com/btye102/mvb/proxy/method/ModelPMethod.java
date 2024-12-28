package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

import java.lang.reflect.Method;

/**
 * Model方法执行
 *
 * @author: rd13
 * @since: 2024/12/26
 **/
public class ModelPMethod implements PMethod {
    private final Method method;

    public ModelPMethod(Method method) {
        this.method = method;
    }

    @Override
    public Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable {
        return method.invoke(model, args);
    }
}
