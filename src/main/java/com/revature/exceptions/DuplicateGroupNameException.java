package com.revature.exceptions;

public class DuplicateGroupNameException extends RuntimeException{
    public DuplicateGroupNameException() {
        super("Group name is already in use");
    }
}
