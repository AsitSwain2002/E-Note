package com.org.NoteMakingApp.ExceptionHandler;

public class AccountInactiveException extends RuntimeException{

	public AccountInactiveException(String msg) {
		super(msg);
	}
}
