package com.org.NoteMakingApp.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.message.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseHandler {

	private String message;
	private int status;
	private Object data;

	public ResponseEntity<?> create() {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", message);
		response.put("data", data);
		response.put("status",status );
		return new ResponseEntity<>(response,HttpStatus.valueOf(status));
	}
	public ResponseEntity<?> createWithoutData() {

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", message);
		response.put("status",status );
		return new ResponseEntity<>(response,HttpStatus.valueOf(status));
	}
}
