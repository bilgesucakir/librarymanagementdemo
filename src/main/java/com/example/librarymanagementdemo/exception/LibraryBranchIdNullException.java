package com.example.librarymanagementdemo.exception;


public class LibraryBranchIdNullException extends RuntimeException {

    public LibraryBranchIdNullException(String message) {
        super(message);
    }

    public LibraryBranchIdNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryBranchIdNullException(Throwable cause) {
        super(cause);
    }
}