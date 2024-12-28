package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;

import java.lang.reflect.Method;

/**
 * @author: rd13
 * @since: 2024/12/27
 **/
public class ToStringPMethod implements PMethod {

    @Override
    public <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException {
        return viewClass.getName() + "/" + model.toString();
    }
}
