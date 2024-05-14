package com.user.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.auth.util.JwtTokenProvider;
import com.auth.vo.Token;
import com.common.dto.Response;
import com.user.mapper.UserMapper;
import com.user.vo.RoleType;
import com.user.vo.User;
import com.user.vo.UserRequest.Login;
import com.user.vo.UserRequest.Logout;
import com.user.vo.UserRequest.Reissue;
import com.user.vo.UserRequest.SignUp;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final Response response;
	private final JwtTokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final RedisTemplate<String, Object> redisTemplate;
	
	private static final String LOGOUT_KEY = "logout";

	@Override
	public ResponseEntity<?> signUp(SignUp signUpRequest) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByEmail(signUpRequest.email());

		if (user != null) {
			return response.fail("이미 가입한 이메일입니다.", HttpStatus.BAD_REQUEST);
		}

		User newUser = User.builder()
				.email(signUpRequest.email())
				.password(passwordEncoder.encode(signUpRequest.password()))
				.role(RoleType.USER.getRole())
				.sidoCode(signUpRequest.sidoCode())
				.gugunCode(signUpRequest.gunguCode())
				.build();

		userMapper.insert(newUser);

		return response.success("회원가입에 성공했습니다.");
	}

	@Override
	public ResponseEntity<?> login(Login loginRequest) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByEmail(loginRequest.email());
		
		if(user == null) {
			return response.fail("해당 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		
		// loginRequest의 ID, PW를 기반으로 Authentication 객체 생성
		// 이때 authentication는 인증되었는지를 확인하는 authenticated 값이 false이다. 즉 아직 인증되지 않았다.
		UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();
		
		// 실제 검증을 수행한다.
		Authentication authentication = authenticationManagerBuilder
				.getObject()
				.authenticate(authenticationToken);
		
		// 인증 정보를 기반으로 하여 token 생성
		Token token = tokenProvider.generateToken(authentication);
		
		// refresh token을 redis에 저장 (expirationTime 설정을 통해 자동 삭제 처리)
		redisTemplate.opsForValue()
			.set("RT:" + authentication.getName(), token.refreshToken(), token.accessTokenExpiredTime(), TimeUnit.MILLISECONDS);
		
		return response.success(token, "로그인에 성공했습니다", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> reissue(Reissue reissueRequest) {
		// TODO Auto-generated method stub
		if(!tokenProvider.validateToken(reissueRequest.refreshToken())) {
			return response.fail("refresh token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = tokenProvider.getAuthentication(reissueRequest.refreshToken());
		String name = authentication.getName();
		
		String refreshToken = (String) redisTemplate.opsForValue()
				.get("RT:" + name);
		
		if(ObjectUtils.isEmpty(refreshToken)) {
			return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		if(!refreshToken.equals(reissueRequest.refreshToken())) {
			return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
		}
		
		Token newToken = tokenProvider.generateToken(authentication);
		
		return response.success(newToken, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> logout(Logout logoutRequest) {
		// TODO Auto-generated method stub
		if(!tokenProvider.validateToken(logoutRequest.accessToken())) {
			return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = tokenProvider.getAuthentication(logoutRequest.accessToken());
		String name = authentication.getName();
		
		String refreshToken = (String) redisTemplate.opsForValue()
				.get("RT:" + name);
		
		if(ObjectUtils.isEmpty(refreshToken)) {
			redisTemplate.delete("RT:" + name);
		}
		
		Long expiration = tokenProvider.getExpiration(logoutRequest.accessToken());
		redisTemplate.opsForValue()
			.set(logoutRequest.accessToken(), LOGOUT_KEY, expiration, TimeUnit.MILLISECONDS);
		
		return response.success("로그아웃 되었습니다.");
	}

}
