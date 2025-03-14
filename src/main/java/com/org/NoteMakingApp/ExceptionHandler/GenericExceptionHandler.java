package com.org.NoteMakingApp.ExceptionHandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public static ResponseEntity<?> resourceNotFoundException(Exception e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.NOT_FOUND.value());
		data.setTime(new Date().toLocaleString());
		return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AlreadyExists.class)
	public static ResponseEntity<?> alreadyExistsException(Exception e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.CONFLICT.value());
		data.setTime(new Date().toLocaleString());
		return new ResponseEntity<>(data, HttpStatus.CONFLICT);
	}
}