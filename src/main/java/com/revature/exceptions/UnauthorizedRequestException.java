package com.revature.exceptions;

public class UnauthorizedRequestException extends RuntimeException{
    public UnauthorizedRequestException(String errorMessage) {
        super(errorMessage);
    }
}
