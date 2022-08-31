package com.stefanini.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.stefanini.models.Client;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${stefaninifood.jwt.expiration}")
	private String expiration;
	
	@Value("${stefaninifood.jwt.secret}")
	private String secret;
	
	public String generateToken(Authentication authentication) {
		Client logged = (Client) authentication.getPrincipal();
		Date today = new Date();
		Date expDate = new Date(today.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do StefaniniFood")
				.setSubject(logged.getId().toString())
				.setIssuedAt(today)
				.setExpiration(expDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getClientId(String token) {
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
