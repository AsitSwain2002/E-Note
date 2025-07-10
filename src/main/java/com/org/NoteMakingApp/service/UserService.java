package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.Dto.UsersDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public boolean registerUser(UsersDto userdto, String reqUrl) throws AlreadyExists, Exception;
}
