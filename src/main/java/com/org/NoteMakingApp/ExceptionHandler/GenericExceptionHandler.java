package com.org.NoteMakingApp.ExceptionHandler;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.hibernate.annotations.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public static ResponseEntity<?> resourceNotFoundException(Exception e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AlreadyExists.class)
	public static ResponseEntity<?> alreadyExistsException(Exception e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public static ResponseEntity<?> illegalArgumentException(IllegalArgumentException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoryValidationEcxception.class)
	public static ResponseEntity<?> CategoryValidationEcxception(CategoryValidationEcxception e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).failedMessages(e.getError())
				.statusCode(HttpStatus.NOT_FOUND.value()).time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoteValidationException.class)
	public static ResponseEntity<?> noteValidationException(NoteValidationException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).failedMessages(e.getErrorRes())
				.statusCode(HttpStatus.NOT_FOUND.value()).time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FileNotFoundException.class)
	public static ResponseEntity<?> noteValidationException(FileNotFoundException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TodoValidationException.class)
	public static ResponseEntity<?> todoValidationException(TodoValidationException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).failedMessages(e.getErrorRes())
				.statusCode(HttpStatus.NOT_FOUND.value()).time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AlreadyVerifiedException.class)
	public static ResponseEntity<?> alreadyVerifiedException(AlreadyVerifiedException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public static ResponseEntity<?> badCredentialsException(BadCredentialsException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(AccountInactiveException.class)
	public static ResponseEntity<?> accountInactiveException(AccountInactiveException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.NOT_FOUND.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(AuthorizationDeniedException.class)
	public static ResponseEntity<?> authorizationDeniedException(AuthorizationDeniedException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.FORBIDDEN.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler(JsonParseException.class)
	public static ResponseEntity<?> jsonParseException(JsonParseException e) {
		ExceptionData data = ExceptionData.builder().message(e.getMessage()).statusCode(HttpStatus.FORBIDDEN.value())
				.time(LocalDateTime.now().toString()).build();
		return GenericResponceBuilder.errorMessage(data, HttpStatus.FORBIDDEN);
	}
}