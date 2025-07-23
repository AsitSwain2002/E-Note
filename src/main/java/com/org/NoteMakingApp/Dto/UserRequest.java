package com.org.NoteMakingApp.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private String oldPassword;
	private String newPassword;

}