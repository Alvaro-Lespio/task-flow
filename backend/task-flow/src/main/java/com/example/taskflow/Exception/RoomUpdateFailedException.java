package com.example.taskflow.Exception;

public class RoomUpdateFailedException extends RuntimeException{
    public RoomUpdateFailedException(String message){
        super(message);
    }
}
