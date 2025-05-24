package com.org.NoteMakingApp.ExceptionHandler;

import java.io.FileNotFoundException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public static ResponseEntity<?> resourceNotFoundException(Exception e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.NOT_FOUND.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AlreadyExists.class)
	public static ResponseEntity<?> alreadyExistsException(Exception e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.CONFLICT.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public static ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.BAD_REQUEST.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoryValidationEcxception.class)
	public static ResponseEntity<?> CategoryValidationEcxception(CategoryValidationEcxception e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setMessages(e.getError());
		data.setStatusCode(HttpStatus.BAD_REQUEST.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoteValidationException.class)
	public static ResponseEntity<?> noteValidationException(NoteValidationException e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setMessages(e.getErrorRes());
		data.setStatusCode(HttpStatus.BAD_REQUEST.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(FileNotFoundException.class)
	public static ResponseEntity<?> noteValidationException(FileNotFoundException e) {
		ExceptionData data = new ExceptionData();
		data.setMessage(e.getMessage());
		data.setStatusCode(HttpStatus.NOT_FOUND.value());
		data.setTime(new Date().toLocaleString());
		return GenericResponceBuilder.errorMessage(data, HttpStatus.NOT_FOUND);
	}

}