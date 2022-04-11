package com.revature.exceptions;

public class NotificationNotFoundExeption extends RuntimeException {
    @Override
    public String getMessage() {
        return "Could not find notification!";
    }
}