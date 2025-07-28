package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.Dto.PasswordRequest;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyVerifiedException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

public interface HomeService {
	public boolean verifyUser(int uId, String vCode) throws ResourceNotFoundException, AlreadyVerifiedException;

	public boolean passwordReset(int userId, String vNum) throws ResourceNotFoundException;

	public void forgetPassword(String username,HttpServletRequest req) throws Exception;

	public boolean restPassword(PasswordRequest password) throws ResourceNotFoundException;

}
