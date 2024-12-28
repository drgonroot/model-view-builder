package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

import java.lang.reflect.Method;

/**
 * RelationModel注解对应方法
 * @author: rd13
 * @since: 2024/12/27
 **/
public class RelationModelPMethod implements PMethod {
    private final Method modelAttrGetMethod;
    private final Object relationModelDao;
    private final Method relationModelDaoGetMethod;

    public RelationModelPMethod(Method modelAttrGetMethod, Object relationModelDao, Method relationModelDaoGetMethod) {
        this.modelAttrGetMethod = modelAttrGetMethod;
        this.relationModelDao = relationModelDao;
        this.relationModelDaoGetMethod = relationModelDaoGetMethod;
    }

    @Override
    public Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable {
        Object attr = modelAttrGetMethod.invoke(model);
        return relationModelDaoGetMethod.invoke(relationModelDao, attr);
    }
}
