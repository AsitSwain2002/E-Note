package com.org.NoteMakingApp.Dto;

import java.util.List;

import com.org.NoteMakingApp.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDto {
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mobile;
	private List<RoleDto> roles;

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RoleDto {

		private int id;
		private String name;
	}
}
