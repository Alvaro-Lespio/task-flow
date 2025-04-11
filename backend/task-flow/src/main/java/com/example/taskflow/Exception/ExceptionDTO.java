package com.example.taskflow.Exception;

public class ExceptionDTO {
    private String message;
    private int statusCode;

    public ExceptionDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ExceptionDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
