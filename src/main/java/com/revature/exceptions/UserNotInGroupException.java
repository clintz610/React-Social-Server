package com.revature.exceptions;

public class UserNotInGroupException extends RuntimeException {

    public UserNotInGroupException() {
        super("User is not a part of the group");
    }
}
