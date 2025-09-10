package com.org.NoteMakingApp.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.org.NoteMakingApp.Dto.MailData;
import com.org.NoteMakingApp.Dto.PasswordRequest;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyVerifiedException;
import com.org.NoteMakingApp.ExceptionHandler.ResourceNotFoundException;
import com.org.NoteMakingApp.Repo.UserRepo;
import com.org.NoteMakingApp.model.UserVerification;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.HomeService;
import com.org.NoteMakingApp.util.CommonUtil;
import com.org.NoteMakingApp.util.MailService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class HomeServiceImpl implements HomeService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private MailService mailServices;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public boolean verifyUser(int uId, String vCode) throws ResourceNotFoundException, AlreadyVerifiedException {
		Users user = userRepo.findById(uId)
				.orElseThrow(() -> new ResourceNotFoundException("User With Id: " + uId + " Not Found"));
		if (user.getUserVerification().getVerificationCode() == null) {
			throw new AlreadyVerifiedException("Already Verified");
		}
		if (user.getUserVerification().getVerificationCode().equals(vCode)) {
			UserVerification userVerification = user.getUserVerification();
			userVerification.setIsActive(true);
			userVerification.setVerificationCode(null);

			userRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean passwordReset(int userId, String vNum) throws ResourceNotFoundException {
		Users user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User With Id: " + userId + " Not Found"));

		if (user.getUserVerification().getPassword_verification().equals(vNum)) {

			user.getUserVerification().setPassword_verification("NULL");

			userRepo.save(user);
			return true;
		}
		return false;
	}
	// Forget Password

	@Override
	public void forgetPassword(String userName, HttpServletRequest req) throws Exception {

		Users user = userRepo.findByEmail(userName);

		String randonNum = UUID.randomUUID().toString();
		user.getUserVerification().setPassword_verification(randonNum);
		userRepo.save(user);
		String reqEndpoint = "/api/v1/home/verify-password-link?userId=";
		String message = "<b>Hii [[user]],</b><br>"
				+ "We received a request to reset your password. Click the button below to reset it: <br>"
				+ "<a href='[[url]]'>Click Here </a><br>"
				+ "If you didn’t request a password reset, you can safely ignore this email. Your password will not change.<br>"
				+ "If you have any questions, feel free to contact our support team.<br><br>" + "Thanks,<br>"
				+ "Enote.com";

		message = message.replace("[[user]]", user.getFirstName());
		message = message.replace("[[url]]", CommonUtil.getUrl(req) + reqEndpoint + user.getId() + "&vNum="
				+ user.getUserVerification().getPassword_verification());

		MailData build = MailData.builder().message(message).subject("Password Reset").title("Password Reset Request")
				.to(user.getEmail()).build();

		mailServices.send(build);
	}

	@Override
	public boolean restPassword(PasswordRequest password) throws ResourceNotFoundException {
		Users user = userRepo.findById(password.getUserId()).orElseThrow(
				() -> new ResourceNotFoundException("User With Id: " + password.getUserId() + " Not Found"));

		if ((!StringUtils.hasText(password.getPassword()) || password.getPassword().length() >= 6)) {
			throw new IllegalArgumentException("Invalid Password");
		}
		user.setPassword(encoder.encode(password.getPassword()));
		Users users = userRepo.save(user);
		if (!ObjectUtils.isEmpty(users)) {
			return true;
		}
		return false;
	}

}
