package com.revature.exceptions;

public class WrongUserException extends Exception {

	@Override
	public String getMessage() {
		return "You are not the correct user to do this!";
	}
}
