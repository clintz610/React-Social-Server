package com.revature.exceptions;

public class DuplicateRequestException extends RuntimeException {
    public DuplicateRequestException(String msg) {
        super(msg);
    }
}
