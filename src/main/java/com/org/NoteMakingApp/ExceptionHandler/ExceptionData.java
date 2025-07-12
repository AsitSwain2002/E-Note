package com.org.NoteMakingApp.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionData {

	private int statusCode;
	private String message;
	private Map<String , Object> failedMessages = new HashMap<String, Object>();
	private String time;
}
