package com.example.tinyurlclone.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddressFormatException extends RuntimeException {
    public AddressFormatException(String message) {
        super(message);
    }
}
