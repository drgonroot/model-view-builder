package com.btye102.mvb.proxy.method;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.PMethodExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RelationModel注解对应方法
 *
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
    public <T> Object invoke(Class<T> viewClass, Method method, Object proxy, Object modelInProxy, Object model, Object[] args, ModelViewBuilder viewBuilder, BuildContext context) throws BuildViewExecuteException {
        try {
            Object attr = modelAttrGetMethod.invoke(model);
            return relationModelDaoGetMethod.invoke(relationModelDao, attr);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new PMethodExecutionException(String.format("%s视图类执行%s方法异常", viewClass.getName(), method.getName()), e);
        }
    }
}
