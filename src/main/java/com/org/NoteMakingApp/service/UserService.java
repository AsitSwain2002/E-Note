package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.Dto.UserRequest;
import com.org.NoteMakingApp.Dto.UserResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public UserResponse userProfile();

	public void resetPassword(UserRequest userRequest);

}
