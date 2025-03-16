package com.org.NoteMakingApp.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class CategoryValidationEcxception extends RuntimeException {

	Map<String, Object> error = new LinkedHashMap<String, Object>();

	public CategoryValidationEcxception(Map<String, Object> error) {
		super("Validation Failed");
		this.error = error;

	}

	public Map<String, Object> getError() {
		return error;
	}
}
