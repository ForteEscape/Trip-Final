package com.user.service;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.service.AuthService;
import com.auth.vo.Token;
import com.user.mapper.UserMapper;
import com.user.vo.LoginRequest;
import com.user.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

	private final UserMapper userMapper;
	private final AuthService authService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	@Transactional
	@Override
	public Token login(LoginRequest request) {
		// TODO Auto-generated method stub
		authService.authenticateLogin(request);
		
		User user = userMapper.selectByEmailAndPassword(request.email(), request.password());
		
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userMapper.selectByUserId(id);
		
		if(user == null) {
			throw new UsernameNotFoundException("user not exists");
		}
		
		return user;
	}

}
