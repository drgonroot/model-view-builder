package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewException;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.PMethodExecutionException;

import java.lang.reflect.InvocationTargetException;
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
    public <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException {
        try {
            Object attr = modelAttrGetMethod.invoke(model);
            Object relationModel = relationModelDaoGetMethod.invoke(relationModelDao, attr); // 关联model信息
            return viewBuilder.build(relationModel, relationView, context);
        } catch (BuildViewException | InvocationTargetException | IllegalAccessException e) {
            throw new PMethodExecutionException(String.format("%s视图类执行%s方法异常", viewClass.getName(), method.getName()), e);
        }
    }
}
