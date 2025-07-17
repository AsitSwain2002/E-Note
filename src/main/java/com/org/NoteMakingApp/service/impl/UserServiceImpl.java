package com.org.NoteMakingApp.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.org.NoteMakingApp.Dto.LoginRequest;
import com.org.NoteMakingApp.Dto.LoginResponse;
import com.org.NoteMakingApp.Dto.MailData;
import com.org.NoteMakingApp.Dto.UsersDto;
import com.org.NoteMakingApp.ExceptionHandler.AlreadyExists;
import com.org.NoteMakingApp.Repo.RoleRepo;
import com.org.NoteMakingApp.Repo.UserRepo;
import com.org.NoteMakingApp.Validation.UserValidation;
import com.org.NoteMakingApp.config.security.UserDetlImpl;
import com.org.NoteMakingApp.model.Role;
import com.org.NoteMakingApp.model.UserVerification;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.JwtService;
import com.org.NoteMakingApp.service.UserService;
import com.org.NoteMakingApp.util.MailService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserValidation userValidation;
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private MailService mailServices;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public boolean registerUser(UsersDto userdto, String reqUrl) throws Exception {

		// Check Duplicate User
		checkUser(userdto);

		// Validation module added Here
		userValidation.validateUser(userdto);

		Users user = mapper.map(userdto, Users.class);
		setRole(userdto, user);
		UserVerification userVerification = UserVerification.builder().isActive(false)
				.verificationCode(UUID.randomUUID().toString()).build();
		user.setUserVerification(userVerification);
		user.setPassword(encoder.encode(userdto.getPassword()));
		Users save = userRepo.save(user);
		if (!ObjectUtils.isEmpty(save)) {

			sendMail(save, reqUrl);
			return true;
		}
		return false;
	}

	private void sendMail(Users user, String reqUrl) throws Exception {

		String message = "Hi, <b> [[userName]] </b> <br><br>" + "Your Account Created Sucessfully"
				+ "<br>Click the below link to account verify <br>" + "<a href='[[url]]'>Click here</a> <br><br>"
				+ "Thanks, <br>" + "Enote.com";

		message = message.replace("[[userName]]", user.getFirstName());
		message = message.replace("[[url]]", reqUrl + "/api/v1/home/verify?uId=" + user.getId() + "&vcode="
				+ user.getUserVerification().getVerificationCode());
		MailData data = MailData.builder().title("Account Create Confirmation").subject("Account Creation Sucessfull")
				.to(user.getEmail()).message(message).build();
		mailServices.send(data);
	}

	private void checkUser(UsersDto userdto) throws AlreadyExists {
		boolean exitsEmail = userRepo.existsByEmail(userdto.getEmail());
		if (exitsEmail) {
			throw new AlreadyExists("User Already Present");
		}
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		if (authenticate.isAuthenticated()) {
			UserDetlImpl cUser = (UserDetlImpl) authenticate.getPrincipal();
			String token = jwtService.getToken(cUser.getUser());
			LoginResponse loginResponse = LoginResponse.builder().user(mapper.map(cUser.getUser(), UsersDto.class))
					.token(token).build();

			return loginResponse;
		}
		return null;
	}

	private void setRole(UsersDto userdto, Users user) {

		List<Integer> reqRoleId = userdto.getRoles().stream().map(r -> r.getId()).toList();
		List<Role> roles = roleRepo.findAllById(reqRoleId);
		user.setRoles(roles);

	}

}
