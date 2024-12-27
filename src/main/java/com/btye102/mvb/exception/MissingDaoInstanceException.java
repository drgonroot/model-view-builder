package com.btye102.mvb.exception;

/**
 * @author: rd13
 * @since: 2024/12/27
 **/
public class MissingDaoInstanceException extends BuildViewReflectException {
    private static final long serialVersionUID = 4163081374785270967L;

    public MissingDaoInstanceException(String message) {
        super(message);
    }
}
