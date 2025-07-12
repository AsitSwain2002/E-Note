package com.org.NoteMakingApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.NoteMakingApp.Dto.LoginRequest;
import com.org.NoteMakingApp.Dto.LoginResponse;
import com.org.NoteMakingApp.Dto.UsersDto;
import com.org.NoteMakingApp.ExceptionHandler.ExceptionData;
import com.org.NoteMakingApp.service.UserService;
import com.org.NoteMakingApp.util.CommonUtil;
import com.org.NoteMakingApp.util.GenericResponceBuilder;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/save-user")
	public ResponseEntity<?> saveUser(@RequestBody UsersDto usersDto, HttpServletRequest req) throws Exception {
		String reqUrl = CommonUtil.getUrl(req);
		boolean registerUser = userService.registerUser(usersDto, reqUrl);
		if (registerUser) {
			return GenericResponceBuilder.withOutData("Register Successfully", HttpStatus.OK);
		} else {
			return GenericResponceBuilder.withOutData("Something Went Wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
		LoginResponse login = userService.login(loginRequest);
		System.out.println();
		System.out.println(login);
		System.out.println();
		if(ObjectUtils.isEmpty(login)) {
			GenericResponceBuilder.errorMessage(ExceptionData.builder().message("Invalid Credintial").build(), HttpStatus.NOT_FOUND);
		}
		return GenericResponceBuilder.withData("Login Sucess", login, HttpStatus.OK);

	}
}
