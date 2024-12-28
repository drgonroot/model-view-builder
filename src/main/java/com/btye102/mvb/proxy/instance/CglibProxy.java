package com.btye102.mvb.proxy.instance;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.builder.ModelViewBuilder;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.BuildViewReflectException;
import com.btye102.mvb.exception.MethodCheckException;
import com.btye102.mvb.proxy.method.PMethod;
import com.btye102.mvb.proxy.method.RawPMethod;
import com.btye102.mvb.utils.ClassUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * @author: rd13
 * @since: 2024/12/28
 **/
public class CglibProxy implements IProxy {

    @Override
    public void checkViewMethod(Method viewMethod) throws BuildViewReflectException {
        if (viewMethod.isDefault()) {
            throw new MethodCheckException(String.format("%s视图不支持代理default原生方法%s， 可使用原生JdkProxy代理", viewMethod.getDeclaringClass().getName(), viewMethod.getName()));
        }
    }

    @Override
    public <T> RawPMethod createRawPMethod(ProxyType proxyType, Class<T> viewClass, Method viewMethod) throws BuildViewReflectException {
        return new RawPMethod(proxyType, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxyInstance(Class<T> viewClass, Object model, BuildContext context) throws BuildViewExecuteException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(viewClass);
        ModelViewBuilder modelViewBuilder = context.getModelViewBuilder();
        Map<Class<?>, Map<String, PMethod>> viewProxyMethodMap = context.getViewProxyMethodMap();
        enhancer.setCallback((MethodInterceptor) (modelInProxy, method, args, proxy) -> {
            Map<String, PMethod> methodMap = viewProxyMethodMap.getOrDefault(method.getDeclaringClass(), Collections.emptyMap());
            String methodParamName = ClassUtils.getMethodParamName(method);
            PMethod proxyMethod = methodMap.get(methodParamName);
            if (proxyMethod != null) {
                return proxyMethod.invoke(viewClass, method, proxy, modelInProxy, model, args, modelViewBuilder, context);
            } else {
                return null;
            }
        });
        return (T) enhancer.create();
    }
}
