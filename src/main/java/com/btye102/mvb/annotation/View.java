package com.btye102.mvb.annotation;

import com.btye102.mvb.builder.GetModel;
import com.btye102.mvb.proxy.instance.ProxyType;

import java.lang.annotation.*;

/**
 * 视图
 * 标识某个类使用是一个view，用于构建工具扫描启动
 * @author: rd13
 * @since: 2024/12/23
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface View {

    /**
     * 对应Model类
     * */
    Class<?> modelClass();

    /**
     * 获取model的dao类
     * */
    Class<?> modelDaoClass() default Object.class;

    /**
     * 获取model的dao类方法接口
     * */
    Class<? super GetModel> modelDaoMethodClass() default GetModel.class;

    /**
     * 代理方式
     * */
    ProxyType proxyType() default ProxyType.JDK;
}
