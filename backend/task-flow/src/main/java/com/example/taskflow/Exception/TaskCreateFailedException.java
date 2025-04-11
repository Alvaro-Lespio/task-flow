package com.example.taskflow.Exception;

public class TaskCreateFailedException extends RuntimeException {
    public TaskCreateFailedException(String message) {
        super(message);
    }
}
