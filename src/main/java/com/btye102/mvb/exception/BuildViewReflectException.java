package com.btye102.mvb.exception;

/**
 * 构建视图反射出现异常
 * @author: rd13
 * @since: 2024/12/27
 **/
public class BuildViewReflectException extends BuildViewException {
    private static final long serialVersionUID = 8176805721030877915L;

    public BuildViewReflectException() {
    }

    public BuildViewReflectException(String message) {
        super(message);
    }

    public BuildViewReflectException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildViewReflectException(Throwable cause) {
        super(cause);
    }

    public BuildViewReflectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
