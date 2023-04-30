package com.example.tinyurlclone.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
public class ErrorResponse {
    private final int status;
    private final String message;
    private final Date timestamp;
    @JsonInclude(Include.NON_NULL)
    private final StackTraceElement[] stackTrace;
    @JsonInclude(Include.NON_NULL)
    private List<Object> errors;

    static public ErrorResponse BadRequestErrorResponse(String message, StackTraceElement[] stackTrace, List<Object> errors) {
        return new ErrorResponse(400, message, new Date(), stackTrace, errors);
    }

    static public ErrorResponse ConflictErrorResponse(String message, StackTraceElement[] stackTrace, List<Object> errors) {
        return new ErrorResponse(409, message, new Date(), stackTrace, errors);
    }
}
