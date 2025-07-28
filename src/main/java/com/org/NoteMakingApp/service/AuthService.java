package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.Dto.LoginRequest;
import com.org.NoteMakingApp.Dto.LoginResponse;
import com.org.NoteMakingApp.Dto.UsersDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

	public boolean registerUser(UsersDto userdto, String reqUrl) throws AlreadyExists, Exception;

	public LoginResponse login(LoginRequest loginRequest);
}
