package com.btye102.mvb.builder;

import com.btye102.mvb.annotation.RelationModel;
import com.btye102.mvb.annotation.RelationView;
import com.btye102.mvb.annotation.View;
import com.btye102.mvb.exception.MissingDaoInstanceException;
import com.btye102.mvb.exception.MissingMethodException;
import com.btye102.mvb.exception.MissingViewException;
import com.btye102.mvb.proxy.method.*;
import com.btye102.mvb.utils.ClassUtils;
import com.sun.istack.internal.NotNull;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模型到视图构建
 *
 * @author: rd13
 * @since: 2024/12/23
 **/
public class ModelViewBuilder {
    private static final String TO_STRING_METHOD = "toString()";
    private final Map<Class<?>, Map<String, Method>> classMethodMap;
    private final Map<Class<?>, Map<String, PMethod>> viewProxyMethodMap;

    private final ModelDaoFactory modelDaoFactory;
    private final PMethodFactory methodFactory;

    public ModelViewBuilder(ModelDaoFactory modelDaoFactory) {
        this(modelDaoFactory, null);
    }

    public ModelViewBuilder(ModelDaoFactory modelDaoFactory, PMethodFactory methodFactory) {
        this.modelDaoFactory = modelDaoFactory;
        this.methodFactory = methodFactory;
        this.viewProxyMethodMap = new ConcurrentHashMap<>();
        this.classMethodMap = new ConcurrentHashMap<>();
        this.viewProxyMethodMap.put(Object.class, Collections.singletonMap(TO_STRING_METHOD, new ToStringPMethod()));
    }

    /**
     * 构建成对应视图数据
     */
    public <T> T build(Object model, Class<T> viewClass, @NotNull BuildContext context) throws Exception {
        if (model == null) {
            return null;
        }
        scanViewClass(viewClass);
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            Map<String, PMethod> methodMap = viewProxyMethodMap.getOrDefault(method.getDeclaringClass(), Collections.emptyMap());
            String methodParamName = ClassUtils.getMethodParamName(method);
            PMethod proxyMethod = methodMap.get(methodParamName);
            if (proxyMethod != null) {
                return proxyMethod.invoke(viewClass, proxy, model, this, context, args);
            } else {
                return null;
            }
        };
        T proxyInstance = (T) Proxy.newProxyInstance(viewClass.getClassLoader(), new Class[]{viewClass}, invocationHandler);
        return proxyInstance;
    }

    private void scanViewClass(Class<?> viewClass) throws Exception {
        View viewAnnotation = viewClass.getAnnotation(View.class);
        if (viewAnnotation == null) {
            return;
        }
        if (viewProxyMethodMap.containsKey(viewClass)) {
            return;
        }
        synchronized (ModelViewBuilder.class) {
            Map<String, Method> viewMethodMap = getClassMethod(viewClass);
            Class<?> modelClass = viewAnnotation.modelClass();
            Map<String, PMethod> viewProxyMap = new HashMap<>();
            for (Map.Entry<String, Method> entry : viewMethodMap.entrySet()) {
                String methodParamName = entry.getKey();
                Method viewMethod = entry.getValue();
                PMethod pMethod = dealRelationModel(modelClass, viewMethod);
                if (pMethod == null) {
                    pMethod = dealRelationView(modelClass, viewMethod);
                }
                if (pMethod == null) {
                    pMethod = dealModelMethod(modelClass, methodParamName);
                }
                if (pMethod == null) {
                    pMethod = dealRawMethod(viewClass, viewMethod);
                }
                if (methodFactory != null) {
                    pMethod = methodFactory.buildNewMethod(pMethod, viewMethod);
                }
                viewProxyMap.put(methodParamName, pMethod);
            }
            viewProxyMethodMap.put(viewClass, viewProxyMap);
        }
    }

    private Map<String, Method> getClassMethod(Class<?> clazz) {
        return classMethodMap.computeIfAbsent(clazz, aClass -> {
            List<Method> methodList = ClassUtils.getMethodList(aClass);
            Map<String, Method> methodMap = new HashMap<>();
            for (Method method : methodList) {
                methodMap.put(ClassUtils.getMethodParamName(method), method);
            }
            return methodMap;
        });
    }

    /**
     * View类方法上注解RelationView
     */
    private PMethod dealRelationView(Class<?> bindModelClass, Method viewMethod) throws Exception {
        RelationView relationViewAnnotation = viewMethod.getAnnotation(RelationView.class);
        if (relationViewAnnotation == null) {
            return null;
        }
        // 处理关联视图逻辑
        Class<?> viewClass = viewMethod.getDeclaringClass();
        Class<?> relationViewClass = relationViewAnnotation.viewClass();
        View viewAnnotation = relationViewClass.getAnnotation(View.class);
        if (viewAnnotation == null) {
            throw new MissingViewException(String.format("%s类中%s方法上的注解RelationView中参数viewClass,没有View注解",
                    viewClass.getName(), viewMethod.getName()));
        }

        String bindModelAttrGetMethod = relationViewAnnotation.bindModelAttrGetMethod();
        Method modelAtttrGetMethod = getClassMethod(bindModelClass).get(bindModelAttrGetMethod);
        if (modelAtttrGetMethod == null) {
            throw new MissingMethodException(String.format("%s类中%s方法上的注解RelationView中参数bindModelAttrGetMethod没有在%s找到对应方法名",
                    viewClass.getName(), viewMethod.getName(), bindModelClass.getName()));
        }
        Class<?> modelDaoClass = viewAnnotation.modelDaoClass();
        Object modelDaoInstance = modelDaoFactory.getModelDao(modelDaoClass);
        if (modelDaoInstance == null) {
            throw new MissingDaoInstanceException(String.format("%s类中%s方法上的注解RelationView中参数modelDaoClass(%s)没有在%s找到对应实例",
                    viewClass.getName(), viewMethod.getName(), modelDaoClass.getName(), modelDaoFactory.getClass().getName()));
        }
        Class<?> modelDaoMethodClass = viewAnnotation.modelDaoMethodClass();
        if (Arrays.stream(modelDaoClass.getInterfaces()).noneMatch(e -> Objects.equals(e, modelDaoMethodClass))) {
            throw new MissingDaoInstanceException(String.format("%s类中%s方法上的注解RelationView中参数modelDaoClass(%s)没有实现接口modelDaoMethodClass(%s)",
                    viewClass.getName(), viewMethod.getName(), modelDaoClass.getName(), modelDaoClass.getName()));
        }
        Method modelDaoMethod = Arrays.stream(GetModel.class.getDeclaredMethods())
                .limit(1)
                .findFirst()
                .orElse(null);
        if (modelDaoMethod == null) {
            throw new MissingDaoInstanceException(String.format("%s类中%s方法上的注解RelationView中参数modelDaoClass(%s)没有实现getModel方法",
                    viewClass.getName(), viewMethod.getName(), modelDaoClass.getName()));
        }
        return new RelationViewPMethod(modelAtttrGetMethod, relationViewClass, modelDaoInstance, modelDaoMethod);
    }

    /**
     * View类方法上注解RelationModel
     */
    private PMethod dealRelationModel(Class<?> bindModelClass, Method viewMethod) throws Exception {
        RelationModel relationModelAnnotation = viewMethod.getAnnotation(RelationModel.class);
        if (relationModelAnnotation == null) {
            return null;
        }
        // 处理关联视图逻辑
        Class<?> viewClass = viewMethod.getDeclaringClass();
        String bindModelAttrGetMethod = relationModelAnnotation.bindModelAttrGetMethod();
        Method modelAtttrGetMethod = getClassMethod(bindModelClass).get(bindModelAttrGetMethod);
        if (modelAtttrGetMethod == null) {
            throw new MissingMethodException(String.format("%s类中%s方法上的注解RelationModel中参数bindModelAttrGetMethod没有在%s找到对应方法名",
                    viewClass.getName(), viewMethod.getName(), bindModelClass.getName()));
        }
        Class<?> modelDaoClass = relationModelAnnotation.modelDaoClass();
        Object modelDaoInstance = modelDaoFactory.getModelDao(modelDaoClass);
        if (modelDaoInstance == null) {
            throw new MissingDaoInstanceException(String.format("%s类中%s方法上的注解RelationModel中参数modelDaoClass(%s)没有在%s找到对应实例",
                    viewClass.getName(), viewMethod.getName(), modelDaoClass.getName(), modelDaoFactory.getClass().getName()));
        }
        String modelDaoMethodName = relationModelAnnotation.modelDaoMethod();
        Method modelDaoMethod = Arrays.stream(modelDaoClass.getDeclaredMethods())
                .filter(e -> Objects.equals(ClassUtils.getMethodParamName(e), modelDaoMethodName))
                .findFirst()
                .orElse(null);
        if (modelDaoMethod == null) {
            throw new MissingDaoInstanceException(String.format("%s类中%s方法上的注解RelationView中参数modelDaoClass(%s)没有实现%s方法",
                    viewClass.getName(), viewMethod.getName(), modelDaoClass.getName(), modelDaoMethodName));
        }
        return new RelationModelPMethod(modelAtttrGetMethod, modelDaoInstance, modelDaoMethod);
    }

    /**
     * View类方法和Model的类方法一致
     */
    private PMethod dealModelMethod(Class<?> modelClass, String methodParamName) {
        Method modelMethod = getClassMethod(modelClass).get(methodParamName);
        if (modelMethod == null) {
            return null;
        }
        return new ModelPMethod(modelMethod);
    }

    /**
     * 仅执行View类方法
     */
    private PMethod dealRawMethod(Class<?> viewClass, Method viewMethod) throws MissingMethodException {
        try {
            if (Objects.equals(viewMethod.getName(), TO_STRING_METHOD)) {
                return new ToStringPMethod();
            } else if (viewMethod.isDefault()) {
                Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                        .getDeclaredConstructor(Class.class, int.class);
                constructor.setAccessible(true);
                Class<?> declaringClass = viewMethod.getDeclaringClass();
                int allModes = MethodHandles.Lookup.PUBLIC | MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE;
                MethodHandle methodHandle = constructor.newInstance(declaringClass, allModes)
                        .unreflectSpecial(viewMethod, declaringClass);
                return new RawPMethod(methodHandle);
            } else {
                throw new MissingMethodException(String.format("%s视图缺少对应原生方法%s", viewClass.getName(), viewMethod.getName()));
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException e) {
            throw new MissingMethodException(String.format("%s视图缺少对应原生方法%s", viewClass.getName(), viewMethod.getName()), e);
        }
    }
}
