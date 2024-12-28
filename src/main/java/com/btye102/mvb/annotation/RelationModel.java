package com.btye102.mvb.annotation;

import java.lang.annotation.*;

/**
 * 关联model数据
 *
 * @author: rd13
 * @since: 2024/12/27
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RelationModel {

    /**
     * model属性方法名，无方法参数
     * eg: getUserId() 这种类似写法
     */
    String bindModelAttrGetMethod();

    /**
     * modelDao的类
     * */
    Class<?> modelDaoClass();

    /**
     * modelDao的方法签名
     * eg: accept(java.lang.Integer) 这种类似写法
     * */
    String modelDaoMethod();
}
