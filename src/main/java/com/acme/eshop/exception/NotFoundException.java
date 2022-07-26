package com.acme.eshop.exception;


public class NotFoundException extends Exception {
    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NotFoundException(final String message) {
        super(message);
    }
}