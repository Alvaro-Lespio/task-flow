package com.example.taskflow.Exception;

public class RoomCreateFailedException extends RuntimeException{
    public RoomCreateFailedException(String message) {
        super(message);
    }
}
