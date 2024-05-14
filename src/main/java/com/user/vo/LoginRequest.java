package com.user.vo;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public record LoginRequest(
		String email,
		String password
) {
	
	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
