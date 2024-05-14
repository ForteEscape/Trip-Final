package com.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;

import com.user.vo.LoginRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public void authenticateLogin(LoginRequest loginRequest) {
		UsernamePasswordAuthenticationToken token = loginRequest.toAuthentication();
		authenticationManagerBuilder.getObject().authenticate(token);
	}

}
