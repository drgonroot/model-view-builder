package com.btye102.mvb.exception;

/**
 * @author: rd13
 * @since: 2024/12/27
 **/
public class MissingMethodException extends BuildViewReflectException {
    private static final long serialVersionUID = 8011316424534743070L;

    public MissingMethodException(String message) {
        super(message);
    }

    public MissingMethodException(String message, Throwable cause) {
        super(message, cause);
    }
}
