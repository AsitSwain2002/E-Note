package com.org.NoteMakingApp.ExceptionHandler;

public class JwtTokenExpaired extends RuntimeException {

	public JwtTokenExpaired(String message) {
		super(message);
	}

}
