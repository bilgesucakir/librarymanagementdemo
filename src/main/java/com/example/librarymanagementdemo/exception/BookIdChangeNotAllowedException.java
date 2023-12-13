package com.example.librarymanagementdemo.exception;


public class BookIdChangeNotAllowedException extends RuntimeException {

    public BookIdChangeNotAllowedException(String message) {
        super(message);
    }

    public BookIdChangeNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookIdChangeNotAllowedException(Throwable cause) {
        super(cause);
    }
}

