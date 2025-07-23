package com.org.NoteMakingApp.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.NoteMakingApp.Dto.UserRequest;
import com.org.NoteMakingApp.Dto.UserResponse;
import com.org.NoteMakingApp.Repo.UserRepo;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.UserService;
import com.org.NoteMakingApp.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;

	@Override
	public void resetPassword(UserRequest userRequest) {

		try {
			Users loggedInUser = CommonUtil.getLoggedInUser();
			if (!encoder.matches(userRequest.getOldPassword(), loggedInUser.getPassword())) {
				throw new IllegalArgumentException("Password Mismatch");
			} else {
				loggedInUser.setPassword(encoder.encode(userRequest.getNewPassword()));
				userRepo.save(loggedInUser);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public UserResponse userProfile() {
		Users loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse responseUser = mapper.map(loggedInUser, UserResponse.class);
		return responseUser;
	}

}
