package com.org.NoteMakingApp.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.org.NoteMakingApp.util.CommonUtil;

public class AuditAwareConfig implements AuditorAware<Integer> {

	@Override
	public Optional<Integer> getCurrentAuditor() {
		// TODO Auto-generated method stub
		return Optional.of(CommonUtil.getLoggedInUser().getId());
	}

}
