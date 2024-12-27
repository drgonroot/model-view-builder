package com.btye102.mvb.exception;

/**
 * @author: rd13
 * @since: 2024/12/27
 **/
public class MissingViewException extends BuildViewReflectException {

    private static final long serialVersionUID = -3641296093670518092L;

    public MissingViewException(String message) {
        super(message);
    }
}
