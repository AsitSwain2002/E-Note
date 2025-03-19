package com.org.NoteMakingApp.ExceptionHandler;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ExceptionData {

	private int statusCode;
	private String message;
	private Map<String , Object> messages;
	private String time;
}
