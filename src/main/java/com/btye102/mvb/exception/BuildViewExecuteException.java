package com.btye102.mvb.exception;

/**
 * 构建视图执行异常
 * @author: rd13
 * @since: 2024/12/28
 **/
public class BuildViewExecuteException extends BuildViewException {
    private static final long serialVersionUID = -1766213896801904926L;

    public BuildViewExecuteException() {
    }

    public BuildViewExecuteException(String message) {
        super(message);
    }

    public BuildViewExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuildViewExecuteException(Throwable cause) {
        super(cause);
    }

    public BuildViewExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
