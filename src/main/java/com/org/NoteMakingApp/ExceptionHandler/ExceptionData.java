package com.org.NoteMakingApp.ExceptionHandler;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ExceptionData {

	private int statusCode;
	private String message;
	private String time;
}
