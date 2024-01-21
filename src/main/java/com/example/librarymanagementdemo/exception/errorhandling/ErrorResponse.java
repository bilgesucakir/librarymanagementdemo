package com.example.librarymanagementdemo.exception.errorhandling;

import java.util.List;

public class ErrorResponse {

    private int status;
    private List<String> errorMessages;
    private long timeStamp;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, List<String> messages, long timeStamp) {
        this.status = status;
        this.errorMessages = messages;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
