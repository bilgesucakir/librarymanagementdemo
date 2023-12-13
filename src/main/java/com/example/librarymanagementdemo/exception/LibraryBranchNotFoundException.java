package com.example.librarymanagementdemo.exception;


public class LibraryBranchNotFoundException extends RuntimeException {

    public LibraryBranchNotFoundException(String message) {
        super(message);
    }

    public LibraryBranchNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LibraryBranchNotFoundException(Throwable cause) {
        super(cause);
    }
}