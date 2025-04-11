package com.example.taskflow.Exception;

public class TaskUpdateFailedException extends RuntimeException{
    public TaskUpdateFailedException(String message){
        super(message);
    }
}
