package com.org.NoteMakingApp.ExceptionHandler;

public class AlreadyVerifiedException extends RuntimeException {

	public AlreadyVerifiedException(String msg) {
		super(msg);
	}
}
