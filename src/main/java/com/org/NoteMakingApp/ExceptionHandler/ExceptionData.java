package com.org.NoteMakingApp.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionData {

	private int statusCode;
	private String message;
	private Map<String , Object> failedMessages = new HashMap<String, Object>();
	private String time;
}
