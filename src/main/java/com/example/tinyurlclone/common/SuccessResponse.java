package com.example.tinyurlclone.common;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class SuccessResponse {

    String message;
    Object data;
    Integer status;
    Date timestamp;

    static ResponseEntity<SuccessResponse> StatusOKReponse(Object data, String message) {
        return ResponseEntity.ok(new SuccessResponse(message, data, 200, new Date()));
    }

    static ResponseEntity<SuccessResponse> StatusCreated(Object data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(message, data, 201, new Date()));
    }
}
