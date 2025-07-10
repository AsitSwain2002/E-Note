package com.org.NoteMakingApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.org.NoteMakingApp.Dto.MailData;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String mailfrom;

	public void send(MailData data) throws Exception {
		MimeMessage createMimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);
		helper.setTo(data.getTo());
		helper.setFrom(mailfrom, data.getTitle());
		helper.setSubject(data.getSubject());
		helper.setText(data.getMessage(),true);

		mailSender.send(createMimeMessage);
	}
} 
