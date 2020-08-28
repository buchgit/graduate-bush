package ru.graduate.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TimeIsOverException extends RuntimeException {
    public TimeIsOverException(String message) {
        super(message);
    }
}
