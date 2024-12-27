package com.btye102.mvb.proxy;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;

import java.lang.reflect.Method;

/**
 * RelationView注解对应方法
 *
 * @author: rd13
 * @since: 2024/12/27
 **/
public class RelationViewPMethod implements PMethod {
    private final Method modelAttrGetMethod;
    private final Class<?> relationView;
    private final Object relationModelDao;
    private final Method relationModelDaoGetMethod;

    public RelationViewPMethod(Method modelAttrGetMethod, Class<?> relationView, Object relationModelDao, Method relationModelDaoGetMethod) {
        this.modelAttrGetMethod = modelAttrGetMethod;
        this.relationView = relationView;
        this.relationModelDao = relationModelDao;
        this.relationModelDaoGetMethod = relationModelDaoGetMethod;
    }

    @Override
    public Object invoke(Class<?> viewClass, Object proxy, Object model, ModelViewBuilder viewBuilder, BuildContext context, Object[] args) throws Throwable {
        Object attr = modelAttrGetMethod.invoke(model);
        Object relationModel = relationModelDaoGetMethod.invoke(relationModelDao, attr); // 关联model信息
        return viewBuilder.build(relationModel, relationView, context);
    }
}
