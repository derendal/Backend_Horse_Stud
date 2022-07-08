package com.example.myHorseServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (value = HttpStatus.CONTINUE )
public class NoChangeException extends RuntimeException{
    public NoChangeException(String brak_zmian) {
    }
}
