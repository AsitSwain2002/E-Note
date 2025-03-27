package com.org.NoteMakingApp.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class NoteValidationException extends RuntimeException {

	Map<String, Object> errorRes = new LinkedHashMap<String, Object>();

	public NoteValidationException(Map<String, Object> errorRes) {
		super("Validation Failed");
		this.errorRes = errorRes;
	}

}
