package com.org.NoteMakingApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
public class NoteMakingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteMakingAppApplication.class, args);
	}

}
