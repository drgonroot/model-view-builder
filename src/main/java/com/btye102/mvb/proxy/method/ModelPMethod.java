package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.PMethodExecutionException;

import java.lang.reflect.InvocationTargetException;
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
    public <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException {
        try {
            return this.method.invoke(model, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PMethodExecutionException(String.format("%s视图类执行%s方法异常", viewClass.getName(), method.getName()), e);
        }
    }
}
