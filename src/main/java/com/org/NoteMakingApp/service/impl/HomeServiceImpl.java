package com.org.NoteMakingApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.NoteMakingApp.ExceptionHandler.AlreadyVerifiedException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.UserRepo;
import com.org.NoteMakingApp.model.UserVerification;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public boolean verifyUser(int uId, String vCode) throws ResourceNotFoundException, AlreadyVerifiedException {
		Users user = userRepo.findById(uId)
				.orElseThrow(() -> new ResourceNotFoundException("User With Id: " + uId + " Not Found"));
		if (user.getUserVerification().getVerificationCode() == null) {
			throw new AlreadyVerifiedException("Already Verified");
		}
		if (user.getUserVerification().getVerificationCode().equals(vCode)) {
			System.out.println("Runn Here");
			UserVerification userVerification = user.getUserVerification();
			userVerification.setIsActive(true);
			userVerification.setVerificationCode(null);

			userRepo.save(user);
			return true;
		}
		return false;
	}

}
