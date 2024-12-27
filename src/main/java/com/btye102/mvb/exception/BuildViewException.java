package com.btye102.mvb.exception;

/**
 * 构建视图异常
 * @author: rd13
 * @since: 2024/12/27
 **/
public class BuildViewException extends Exception {
    private static final long serialVersionUID = -3738997279000158819L;

    public BuildViewException() {
    }

    public BuildViewException(String message) {
        super(message);
    }

    public BuildViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildViewException(Throwable cause) {
        super(cause);
    }

    public BuildViewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
