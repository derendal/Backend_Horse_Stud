package com.example.myHorseServer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class GamerStudNotFound extends RuntimeException{

    public GamerStudNotFound(String format) {
    }
}
