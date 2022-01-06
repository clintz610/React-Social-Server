package com.revature.exceptions;

public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException() {
        super("Supplied group name was not found");
    }
}
