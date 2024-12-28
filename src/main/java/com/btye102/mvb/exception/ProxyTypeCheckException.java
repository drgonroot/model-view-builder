package com.btye102.mvb.exception;

/**
 * @author: rd13
 * @since: 2024/12/28
 **/
public class ProxyTypeCheckException extends BuildViewExecuteException {
    private static final long serialVersionUID = -1045461669427973151L;

    public ProxyTypeCheckException() {
    }

    public ProxyTypeCheckException(String message) {
        super(message);
    }

    public ProxyTypeCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxyTypeCheckException(Throwable cause) {
        super(cause);
    }

    public ProxyTypeCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
