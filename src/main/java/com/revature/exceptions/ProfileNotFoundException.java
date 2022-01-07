package com.revature.exceptions;

public class ProfileNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Could not find profile!";
    }
}