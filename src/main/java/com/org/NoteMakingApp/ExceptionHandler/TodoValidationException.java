package com.org.NoteMakingApp.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class TodoValidationException extends RuntimeException {

	Map<String, Object> errorRes = new HashMap<String, Object>();

	public TodoValidationException(Map<String, Object> error) {
		super("validation Failed");
		this.errorRes = error;
	}
}
