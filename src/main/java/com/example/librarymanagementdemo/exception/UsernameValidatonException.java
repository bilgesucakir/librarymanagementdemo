package com.example.librarymanagementdemo.exception;

public class UsernameValidatonException extends RuntimeException {

    public UsernameValidatonException(String message) {
        super(message);
    }

    public UsernameValidatonException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameValidatonException(Throwable cause) {
        super(cause);
    }
}
