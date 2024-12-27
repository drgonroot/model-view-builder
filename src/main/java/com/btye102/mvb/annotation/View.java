package com.btye102.mvb.annotation;

import com.btye102.mvb.builder.GetModel;

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

    Class<?> modelDaoClass() default Object.class;

    Class<? super GetModel> modelDaoMethodClass() default GetModel.class;
}
