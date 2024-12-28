package com.btye102.mvb.exception;

/**
 * 视图注解校验异常
 * eg: 视图注解不存在
 * @author: rd13
 * @since: 2024/12/27
 **/
public class ViewAnnotationCheckException extends BuildViewReflectException {

    private static final long serialVersionUID = -3641296093670518092L;

    public ViewAnnotationCheckException(String message) {
        super(message);
    }
}
