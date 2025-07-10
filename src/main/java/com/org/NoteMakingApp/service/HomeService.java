package com.org.NoteMakingApp.service;

import com.org.NoteMakingApp.ExceptionHandler.AlreadyVerifiedException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;

public interface HomeService {
	public boolean verifyUser(int uId, String vCode) throws ResourceNotFoundException, AlreadyVerifiedException;
}
