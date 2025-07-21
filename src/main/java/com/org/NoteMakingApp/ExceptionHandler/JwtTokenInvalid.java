package com.org.NoteMakingApp.ExceptionHandler;

public class JwtTokenInvalid extends RuntimeException {

	public JwtTokenInvalid(String message) {
		super(message);
	}

}
