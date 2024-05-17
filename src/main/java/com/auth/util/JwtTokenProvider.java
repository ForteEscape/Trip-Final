package com.auth.util;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth.vo.Token;
import com.common.exception.CustomException;
import com.user.vo.UserResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	
	private static final String TOKEN_PREFIX = "Bearer ";

	private final Key key;
	//private static final int VALID_TIME = 1000 * 60 * 60 * 24;
	private static final int VALID_TIME = 1000 * 60 * 30;
	private static final int REFRESH_VALID_TIME = 1000 * 60 * 60 * 24 * 2;
	private static final String AUTHORITIES_KEY = "auth";
	
	public JwtTokenProvider(
		@Value("${jwt.secret-key}") String secreatKey
	) {
		byte[] keyBytes = Decoders.BASE64.decode(secreatKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	// JWT Token을 생성하는 메서드
	// 인증이 완료된 Authentication 객체를 이용하여 토큰 생성
	// 이때 claim에는 해당 인원의 권한(authority)과 만료 일자가 들어간다.
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
				.claim(AUTHORITIES_KEY, authorities)
				.setExpiration(accessTokenExpireDate)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		String refreshToken = Jwts.builder()
				.setExpiration(new Date(nowTime + REFRESH_VALID_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		Token token = new Token(accessToken, refreshToken, new Date(nowTime + VALID_TIME).getTime());
		
		return token;
	}
	
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		
		if(claims.get(AUTHORITIES_KEY) == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
	public Authentication checkRefreshToken(String refreshToken) throws ExpiredJwtException {
		Claims claims = parseClaims(refreshToken);
		
		UserDetails user = new User(claims.getSubject(), "", null);
		
		return new UsernamePasswordAuthenticationToken(user, "", null);
	}
	
	public boolean validateToken(String accessToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken);
			
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("wrong JWT sign");
			return false;
		} catch (ExpiredJwtException e) {
			log.info("expired JWT Token");
			return false;
		} catch (UnsupportedJwtException e) {
			log.info("not support JWT Token");
			return false;
		} catch (IllegalArgumentException e) {
			log.info("wrong JWT Token");
			return false;
		}
	}
	
	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
	
	public Long getExpiration(String accessToken) {
		Date expiration = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody()
				.getExpiration();
		
		Long now = (new Date()).getTime();
		
		return (expiration.getTime() - now);
	}
}
