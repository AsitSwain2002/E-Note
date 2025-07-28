package com.org.NoteMakingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.PasswordRequest;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyVerifiedException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.service.HomeService;
import com.org.NoteMakingApp.util.CommonUtil;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeControler {

	@Autowired
	private HomeService homeService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUser(@RequestParam int uId, @RequestParam String vcode, HttpServletRequest request)
			throws ResourceNotFoundException, AlreadyVerifiedException {
		boolean verifyUser = homeService.verifyUser(uId, vcode);
		if (verifyUser) {
			return GenericResponceBuilder.withOutData("Verification Sucessfull", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withOutData("Invalid Verification Link", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/forget-password")
	public ResponseEntity<?> forgetPassword(@RequestParam String username, HttpServletRequest req) throws Exception {
		homeService.forgetPassword(username, req);
		return GenericResponceBuilder.withOutData("Email Sent Sucessfully", HttpStatus.OK);
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody PasswordRequest passwordRequest) throws Exception {

		boolean check = homeService.restPassword(passwordRequest);
		if (check) {
			return GenericResponceBuilder.withOutData("Password Update Sucessfully", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withOutData("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/verify-password-link")
	public ResponseEntity<?> verifyPassword(@RequestParam int userId, @RequestParam String vNum,
			HttpServletRequest request) throws ResourceNotFoundException {
		boolean verifyUser = homeService.passwordReset(userId, vNum);
		if (verifyUser) {
			return GenericResponceBuilder.withOutData("Verification Sucessfull", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withOutData("Invalid Verification Link", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
