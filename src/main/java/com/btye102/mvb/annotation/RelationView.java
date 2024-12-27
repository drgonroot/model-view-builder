package com.btye102.mvb.annotation;

import java.lang.annotation.*;

/**
 * 关联视图
 * 标识某个方法需要获取Model数据并转换视图数据
 * @author: rd13
 * @since: 2024/12/23
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RelationView {
    /**
     * model属性方法名，无方法参数
     * */
    String bindModelAttrGetMethod();

    Class<?> viewClass();
}
