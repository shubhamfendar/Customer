//package com.customer.jwtSecurity;
//
//import java.util.Base64;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import com.customer.model.Customer;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtTokenHelper {
//
//	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//
//	private String secret = "jwtTokenKey";
//
//	// retrive username from jwt token
//	public String getUsernameFromToken(String token) {
//		return getClaimsFromToken(token,Claims::getSubject);
//	}
//
//	public Date getExpirationDateFromToken(String token) {
//		return getClaimsFromToken(token,Claims::getExpiration);
//	}
//
//	public<T> T getClaimsFromToken(String token,Function<Claims,T> claimsResolver) {
//		final Claims claims= getAllClaimsFromToken(token);
//		return claimsResolver.apply(claims);
//	}
//	
//	// for retrieving any information from token we will need the secret key
//	private Claims getAllClaimsFromToken(String token) {
//		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//	}
//	
//	//Check if token expired
//	public boolean isTokenExpired(String token) {
//		final Date expiration= getExpirationDateFromToken(token);
//		return expiration.before(new Date());
//	}
//	
//	// generate token from user
//	public String generateToken(Customer customer) {
//		Map<String,Object> claims=new HashMap<>();
//		return doGenerateToken(claims,customer.getName());
//	}
//	
//	public String doGenerateToken(Map<String,Object> claims,String subject) {
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
//				.signWith(SignatureAlgorithm.HS512, secret)
//				.compact();
//	}
//	
//	public boolean validateToken(String token,UserDetails userDetails) {
//		final String name=getUsernameFromToken(token);
//		return(name.equals(userDetails.getUsername())&& !isTokenExpired(token));
//	}
//	
//}
