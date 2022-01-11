package com.revature.common.dtos;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ErrorResponse {

    String time;
    String message;
    String cause;
    int statusCode;

    public ErrorResponse() {
        super();
        time = LocalDateTime.now().toString();
    }

    public ErrorResponse(int statusCode, Throwable t) {
        this();
        this.statusCode = statusCode;
        message = t.getMessage();
        cause = t.getClass().getSimpleName();
    }

}
