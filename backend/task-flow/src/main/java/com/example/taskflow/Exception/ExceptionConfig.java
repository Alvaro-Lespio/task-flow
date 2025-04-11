package com.example.taskflow.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(UserCreateFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(UserCreateFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The user was not created correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserLoginFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(UserLoginFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The user was not logged correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskCreateFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(TaskCreateFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The task was not created correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskUpdateFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(TaskUpdateFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The task was not updated correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoomCreateFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(RoomCreateFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The room was not created correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RoomUpdateFailedException.class)
    public ResponseEntity<ExceptionDTO> handleException(RoomUpdateFailedException ex){
        ExceptionDTO exceptionDTO = new ExceptionDTO("The room was not updated correctly ",406);
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }
}
