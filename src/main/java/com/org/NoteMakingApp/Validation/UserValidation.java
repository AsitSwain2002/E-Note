package com.org.NoteMakingApp.Validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.org.NoteMakingApp.Dto.UsersDto;
import com.org.NoteMakingApp.Repo.RoleRepo;
import com.org.NoteMakingApp.util.Constant;

@Component
public class UserValidation {

	@Autowired
	private RoleRepo roleRepo;

	public void validateUser(UsersDto usersDto) {
		if (ObjectUtils.isEmpty(usersDto)) {
			throw new IllegalArgumentException("User Data can't be null");
		} else {
			if (!StringUtils.hasText(usersDto.getFirstName())) {
				throw new IllegalArgumentException("Invalid Firsename");
			}
			if (!StringUtils.hasText(usersDto.getLastName())) {
				throw new IllegalArgumentException("Invalid Lastname");
			}
			if (!StringUtils.hasText(usersDto.getEmail()) || !usersDto.getEmail().matches(Constant.EMAIL_REGX)) {
				throw new IllegalArgumentException("Invalid Email");
			}
			if ((!StringUtils.hasText(usersDto.getMobile()) || !usersDto.getMobile().matches(Constant.MOBILENO_REGX))) {
				throw new IllegalArgumentException("Invalid MobileNo");
			}
			if ((!StringUtils.hasText(usersDto.getPassword()) || usersDto.getPassword().length() < 8)) {
				throw new IllegalArgumentException("Invalid Password");
			}
			if (CollectionUtils.isEmpty(usersDto.getRoles())) {
				throw new IllegalArgumentException("Invalid Role");
			} else {
				List<Integer> roleIds = roleRepo.findAll().stream().map(r -> r.getId()).toList();
				List<Integer> reqRole = usersDto.getRoles().stream().map(r -> r.getId())
						.filter(r -> !roleIds.contains(r)).toList();
				if (!CollectionUtils.isEmpty(reqRole)) {
					throw new IllegalArgumentException("Invalid Role");
				}
			}
		}
	}
}
