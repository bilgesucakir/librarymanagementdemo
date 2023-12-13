package com.example.librarymanagementdemo.exception;
public class LibraryUserIdNullException extends RuntimeException {

    public LibraryUserIdNullException(String message) {
        super(message);
    }

    public LibraryUserIdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryUserIdNullException(Throwable cause) {
        super(cause);
    }
}