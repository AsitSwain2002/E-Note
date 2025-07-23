package com.org.NoteMakingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.UserRequest;
import com.org.NoteMakingApp.Dto.UserResponse;
import com.org.NoteMakingApp.service.UserService;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<?> userProfile() {
		UserResponse userProfile = userService.userProfile();
		return GenericResponceBuilder.withData("Fetched", userProfile, HttpStatus.OK);
	}

	@PostMapping("/pasword-change")
	public ResponseEntity<?> passwordChange(@RequestBody UserRequest userRequest) {
		userService.resetPassword(userRequest);
		return GenericResponceBuilder.withOutData("Password Reset Succesfully", HttpStatus.OK);
	}
}
