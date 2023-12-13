package com.example.librarymanagementdemo.exception;


public class LibraryUserNotFoundException extends RuntimeException {

    public LibraryUserNotFoundException(String message) {
        super(message);
    }

    public LibraryUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryUserNotFoundException(Throwable cause) {
        super(cause);
    }
}