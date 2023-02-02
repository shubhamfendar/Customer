package com.customer.jwtSecurity;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("{app.secret}")
	private String secret;

	// 1. Generate Token

	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuer("SF").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(secret.getBytes()))
				.compact();
	}

	// 2. Read Token /Claims

	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
	}

	// 3. Read Exp Date
	
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	// 4. Read Subject / username
	
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	// 5 Validate Exp Date
	
	public boolean isTknExp(String token) {
		Date expDate= getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	// 6. Validate username in token and database , expDate
	
	public boolean validateToken(String username,String token) {
		String tokenUserName=getUsername(token);
		return (username.equals(tokenUserName) && !isTknExp(token));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
