package com.example.librarymanagementdemo.exception;

public class CheckoutNotFoundException extends RuntimeException {

    public CheckoutNotFoundException(String message) {
        super(message);
    }

    public CheckoutNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckoutNotFoundException(Throwable cause) {
        super(cause);
    }
}