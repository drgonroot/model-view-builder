package com.btye102.mvb.builder;

/**
 * ModelDao工厂类
 * 配合@View/@RelationModel的modelDaoClass使用
 * 解决类似dao/service实例获取
 * @author: rd13
 * @since: 2024/12/24
 **/
public interface ModelDaoFactory {

    <T> T getModelDao(Class<T> modelDaoClass);
}
