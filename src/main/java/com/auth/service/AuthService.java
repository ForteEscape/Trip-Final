package com.auth.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

	private final UserMapper userMapper;

	// 검증 시 사용하는 method
	// AbstractUserDetailsAuthenticationProvider 에서 사용
	// 이후 UserDetails를 반환받고, 입력으로 들어온 token을 복호화하여 얻은 password와 db에 저장된 password를
	// 비교하여 비밀번호 일치를 확인한다.
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.user.entity.User user = userMapper.selectByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("user not exists");
		}

		return createUserDetails(user);
	}

	private UserDetails createUserDetails(com.user.entity.User user) {
		return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
	}

}
