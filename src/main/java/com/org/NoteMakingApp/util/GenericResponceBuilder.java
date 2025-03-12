package com.org.NoteMakingApp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class GenericResponceBuilder {

	public static ResponseEntity<?> builder(String message, Object data, HttpStatusCode status) {

		GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
		genericResponseHandler.setMessage(message);
		genericResponseHandler.setData(data);
		genericResponseHandler.setStatus(status.value());
		return genericResponseHandler.create();
	}
	public static ResponseEntity<?> builder(String message,  HttpStatusCode status) {

		GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
		genericResponseHandler.setMessage(message);
		genericResponseHandler.setStatus(status.value());
		return genericResponseHandler.create();
	}
}
