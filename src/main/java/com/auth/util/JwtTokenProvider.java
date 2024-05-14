package com.auth.util;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth.vo.Token;
import com.common.exception.CustomException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	private static final String TOKEN_PREFIX = "Bearer ";
	private final String issuer;
	private final Key key;
	private static final int VALID_TIME = 1000 * 60 * 60 * 24;
	private static final int REFRESH_VALID_TIME = 1000 * 60 * 60 * 24 * 2;
	
	public JwtTokenProvider(
		@Value("${jwt.issuer}") String issuer,
		@Value("${jwt.secret-key}") String secreatKey
	) {
		byte[] keyBytes = Decoders.BASE64.decode(secreatKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
		this.issuer = issuer;
	}
	
	public Token generateToken(Authentication authentication) {
		String authorities = authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		Date now = new Date();
		long nowTime = now.getTime();
		
		Date accessTokenExpireDate = new Date(nowTime + VALID_TIME);
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(accessTokenExpireDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(nowTime + REFRESH_VALID_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return new Token(accessToken, refreshToken);
	}
	
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		
		if(claims.get("auth") == null) {
			throw new CustomException();
		}
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		return null;
	}
	
	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJwt(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
