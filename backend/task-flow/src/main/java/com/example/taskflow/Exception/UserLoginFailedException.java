package com.example.taskflow.Exception;

public class UserLoginFailedException extends RuntimeException{
    public UserLoginFailedException(String message){
        super(message);
    }
}
