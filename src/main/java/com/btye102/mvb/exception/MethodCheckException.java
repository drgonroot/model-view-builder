package com.btye102.mvb.exception;

/**
 * 方法校验异常
 * @author: rd13
 * @since: 2024/12/27
 **/
public class MethodCheckException extends BuildViewReflectException {
    private static final long serialVersionUID = 8011316424534743070L;

    public MethodCheckException(String message) {
        super(message);
    }

    public MethodCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}
