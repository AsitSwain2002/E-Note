package com.org.NoteMakingApp.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private String generateToken = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator instance = KeyGenerator.getInstance("HmacSHA256");
			SecretKey key = instance.generateKey();
			generateToken = Base64.getEncoder().encodeToString(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getToken(Users user) {

		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", user.getRoles());
		claims.put("status", user.getUserVerification().getIsActive());

		String token = Jwts.builder().claims().add(claims).subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 10)).and().signWith(getKey()).compact();
		return token;
	}

	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(generateToken);
		return Keys.hmacShaKeyFor(decode);
	}

}
