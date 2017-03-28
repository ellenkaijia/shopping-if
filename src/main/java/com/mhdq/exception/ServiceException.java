package com.mhdq.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 988592186626034036L;

    public ServiceException() {
        super();
    }

    public ServiceException(String arg0) {
        super(arg0);
    }

    public ServiceException(Throwable arg1) {
        super(arg1);
    }

    public ServiceException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }
}
