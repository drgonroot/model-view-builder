package com.btye102.mvb.proxy;

import java.lang.reflect.Method;

/**
 * PMethod 方法工厂
 * @author: rd13
 * @since: 2024/12/27
 **/
public interface PMethodFactory {

    PMethod buildNewMethod(PMethod pMethod, Method viewMethod );
}
