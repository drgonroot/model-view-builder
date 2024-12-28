package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

/**
 * @author: rd13
 * @since: 2024/12/27
 **/
public class ToStringPMethod implements PMethod {
    @Override
    public Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable {
        return viewClass.getName() + "/" + model.toString();
    }
}
