package com.example.librarymanagementdemo.exception;


public class EntityIdChangeNotAllowedException extends RuntimeException {

    public EntityIdChangeNotAllowedException(String message) {
        super(message);
    }

}

