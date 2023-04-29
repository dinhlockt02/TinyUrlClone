package com.example.tinyurlclone.exception;

import com.example.tinyurlclone.common.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        boolean stackTraceincluded = Objects.equals(request.getParameter("trace"), "true");

        List<String> errors = MethodArgumentNotValidException.errorsToStringList(ex.getAllErrors());
        ErrorResponse errorResponse = ErrorResponse.BadRequestErrorReponse(
                "validation failed",
                stackTraceincluded ? ex.getStackTrace() : null, Collections.singletonList(errors)
        );

        return ResponseEntity.badRequest().body(errorResponse);

    }


    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {

        boolean stackTraceincluded = Objects.equals(request.getParameter("trace"), "true");



        ErrorResponse errorResponse = ErrorResponse.BadRequestErrorReponse(
                ex.getMessage(),
                stackTraceincluded ? ex.getStackTrace() : null, null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
