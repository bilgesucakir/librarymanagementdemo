package com.example.librarymanagementdemo.exception;

public class BookIdNullException extends RuntimeException {

    public BookIdNullException(String message) {
        super(message);
    }

    public BookIdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookIdNullException(Throwable cause) {
        super(cause);
    }
}