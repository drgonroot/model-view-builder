package com.btye102.mvb.builder;

/**
 * 获取model的接口
 * @author: rd13
 * @since: 2024/12/27
 **/
public interface GetModel<K, V> {
    V getModel(K k);
}
