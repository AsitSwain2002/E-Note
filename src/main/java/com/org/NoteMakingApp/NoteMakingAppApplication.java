package com.org.NoteMakingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableScheduling
public class NoteMakingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteMakingAppApplication.class, args);
	}

}
   
  
 