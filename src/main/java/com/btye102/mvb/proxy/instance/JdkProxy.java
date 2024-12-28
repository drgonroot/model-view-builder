package com.btye102.mvb.proxy.instance;

import com.btye102.mvb.builder.BuildContext;
import com.btye102.mvb.exception.BuildViewExecuteException;
import com.btye102.mvb.exception.BuildViewReflectException;
import com.btye102.mvb.exception.MethodCheckException;
import com.btye102.mvb.exception.ViewAnnotationCheckException;
import com.btye102.mvb.proxy.method.PMethod;
import com.btye102.mvb.proxy.method.RawPMethod;
import com.btye102.mvb.utils.ClassUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;

/**
 * @author: rd13
 * @since: 2024/12/28
 **/
public class JdkProxy implements IProxy {

    @Override
    public <T> void checkViewClass(Class<T> viewClass) throws BuildViewReflectException {
        if (!viewClass.isInterface()) {
            throw new ViewAnnotationCheckException(String.format("%s视图类不是接口，无法支持JdkProxy", viewClass.getName()));
        }
    }

    @Override
    public <T> RawPMethod createRawPMethod(ProxyType proxyType, Class<T> viewClass, Method viewMethod) throws BuildViewReflectException {
        try {
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class, int.class);
            constructor.setAccessible(true);
            Class<?> declaringClass = viewMethod.getDeclaringClass();
            int allModes = MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE;
            MethodHandle methodHandle = constructor.newInstance(declaringClass, allModes)
                    .unreflectSpecial(viewMethod, declaringClass);
            return new RawPMethod(proxyType, methodHandle);
        }  catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                  InstantiationException e) {
            throw new MethodCheckException(String.format("%s视图缺少对应原生方法%s", viewClass.getName(), viewMethod.getName()), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxyInstance(Class<T> viewClass, Object model, BuildContext context) throws BuildViewExecuteException {
        Map<Class<?>, Map<String, PMethod>> viewProxyMethodMap = context.getViewProxyMethodMap();
        return (T) Proxy.newProxyInstance(viewClass.getClassLoader(), new Class[]{viewClass}, (proxy, method, args) -> {
            Map<String, PMethod> methodMap = viewProxyMethodMap.getOrDefault(method.getDeclaringClass(), Collections.emptyMap());
            String methodParamName = ClassUtils.getMethodParamName(method);
            PMethod proxyMethod = methodMap.get(methodParamName);
            if (proxyMethod != null) {
                return proxyMethod.invoke(viewClass, method, proxy, null, model, args, context.getModelViewBuilder(), context);
            } else {
                return null;
            }
        });
    }
}
