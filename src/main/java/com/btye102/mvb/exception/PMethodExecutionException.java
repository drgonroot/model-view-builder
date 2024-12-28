package com.btye102.mvb.exception;

/**
 * PMethod执行情况
 * @author: rd13
 * @since: 2024/12/28
 **/
public class PMethodExecutionException extends BuildViewExecuteException {
    private static final long serialVersionUID = 2959958698537909200L;

    public PMethodExecutionException() {
    }

    public PMethodExecutionException(String message) {
        super(message);
    }

    public PMethodExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PMethodExecutionException(Throwable cause) {
        super(cause);
    }

    public PMethodExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
