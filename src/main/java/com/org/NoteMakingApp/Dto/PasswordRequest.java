package com.org.NoteMakingApp.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {

	private int userId;
	private String password;
}
