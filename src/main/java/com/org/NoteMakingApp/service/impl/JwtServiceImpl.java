package com.org.NoteMakingApp.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.org.NoteMakingApp.Dto.UsersDto.RoleDto;
import com.org.NoteMakingApp.config.security.UserDetlImpl;
import com.org.NoteMakingApp.model.Role;
import com.org.NoteMakingApp.model.Users;
import com.org.NoteMakingApp.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private String token = "";

	public JwtServiceImpl() {
		try {
			KeyGenerator gToken = KeyGenerator.getInstance("HmacSHA256");
			SecretKey generateKey = gToken.generateKey();
			token = Base64.getEncoder().encodeToString(generateKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String getToken(Users user) {

		Map<String, Object> claim = new HashMap<String, Object>();
		claim.put("role", user.getRoles());
		claim.put("status", user.getUserVerification().getIsActive());
		return Jwts.builder().claims().add(claim).subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 + 10)).and().signWith(getKey())
				.compact();
	}

	private Key getKey() {
		byte[] decode = Decoders.BASE64.decode(token);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public String extractUsername(String token) {

		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(decryptKey()).build().parseSignedClaims(token).getPayload();
	}

	private SecretKey decryptKey() {
		byte[] decode = Decoders.BASE64.decode(token);
		return Keys.hmacShaKeyFor(decode);
	}

	@Override
	public boolean validateToken(String token, UserDetails detlImpl) {
		Claims extractAllClaims = extractAllClaims(token);
		boolean isExpaired = extractAllClaims.getExpiration().before(new Date(System.currentTimeMillis()));
		if (extractUsername(token).equalsIgnoreCase(detlImpl.getUsername()) && !isExpaired) {
			return true;
		}
		return false;
	}

	@Override
	public List getRole(String token) {
		Claims extractAllClaims = extractAllClaims(token);
		List role = (List) extractAllClaims.get("role");
		return role;
	}

}
