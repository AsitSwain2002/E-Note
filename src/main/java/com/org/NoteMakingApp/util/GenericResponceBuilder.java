package com.org.NoteMakingApp.util;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.org.NoteMakingApp.ExceptionHandler.ExceptionData;

public class GenericResponceBuilder {

	public static ResponseEntity<?> withData(String message, Object data, HttpStatusCode status) {

		GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
		genericResponseHandler.setMessage(message);
		genericResponseHandler.setData(data);
		genericResponseHandler.setStatus(status.value());
		return genericResponseHandler.create();
	}

	public static ResponseEntity<?> withOutData(String message, HttpStatusCode status) {

		GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
		genericResponseHandler.setMessage(message);
		genericResponseHandler.setStatus(status.value());
		return genericResponseHandler.createWithoutData();
	}

	public static ResponseEntity<?> errorMessage(ExceptionData message, HttpStatusCode status) {

		GenericResponseHandler genericResponseHandler = new GenericResponseHandler();
		genericResponseHandler.setData(message);
		genericResponseHandler.setStatus(status.value());
		genericResponseHandler.setMessage("Failed");
		return genericResponseHandler.create();
	}
}
