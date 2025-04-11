package com.example.taskflow.Exception;

public class UserCreateFailedException extends RuntimeException{
    public UserCreateFailedException(String message){
        super(message);
    }
}
