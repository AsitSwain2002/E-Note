package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.model.Users;

public interface JwtService {

	public String getToken(Users user);
}
