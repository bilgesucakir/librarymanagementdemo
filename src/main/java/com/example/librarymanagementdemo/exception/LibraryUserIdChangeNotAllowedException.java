package com.example.librarymanagementdemo.exception;

public class LibraryUserIdChangeNotAllowedException extends RuntimeException {

    public LibraryUserIdChangeNotAllowedException(String message) {
        super(message);
    }

    public LibraryUserIdChangeNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryUserIdChangeNotAllowedException(Throwable cause) {
        super(cause);
    }
}

