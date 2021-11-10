package com.revature.exceptions;

public class PostNotFoundException extends Exception {

	@Override
	public String getMessage() {
		return "Could not find post!";
	}
}
