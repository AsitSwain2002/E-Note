package com.org.NoteMakingApp.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.UserResponse;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.util.CommonUtil;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/profile")
	public ResponseEntity<?> userProfile() {
		Users loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse responseUser = mapper.map(loggedInUser, UserResponse.class);
		return GenericResponceBuilder.withData("Fetched", responseUser, HttpStatus.OK);
	}
}
