package com.revature.common.dtos;

import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "time='" + time + '\'' +
                ", message='" + message + '\'' +
                ", cause='" + cause + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }
}
