package com.acme.eshop.exception;


public class BusinessException extends Exception {
    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BusinessException(final String message) {
        super(message);
    }
}