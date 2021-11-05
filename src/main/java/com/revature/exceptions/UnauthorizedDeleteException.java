package com.revature.exceptions;

public class UnauthorizedDeleteException extends Exception{
    public UnauthorizedDeleteException(String errorMessage) {
        super(errorMessage);
    }
}
