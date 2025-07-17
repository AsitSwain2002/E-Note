package com.org.NoteMakingApp.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.org.NoteMakingApp.config.security.UserDetlImpl;
import com.org.NoteMakingApp.model.Users;

public interface JwtService {

	public String getToken(Users user);

	public String extractUsername(String token);

	public boolean validateToken(String token, UserDetails detlImpl);

	public List getRole(String token);
}
	