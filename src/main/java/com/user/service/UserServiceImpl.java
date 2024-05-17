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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.auth.util.JwtTokenProvider;
import com.auth.vo.Token;
import com.common.dto.Response;
import com.user.entity.User;
import com.user.entity.UserInfo;
import com.user.mapper.UserMapper;
import com.user.util.UserCodeGenerator;
import com.user.vo.RoleType;
import com.user.vo.UserRequest.Login;
import com.user.vo.UserRequest.Logout;
import com.user.vo.UserRequest.Password;
import com.user.vo.UserRequest.PasswordUpdate;
import com.user.vo.UserRequest.Reissue;
import com.user.vo.UserRequest.SignUp;
import com.user.vo.UserRequest.Update;
import com.user.vo.UserResponse;

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
	private final UserCodeGenerator userCodeGenerator;
	
	private static final String LOGOUT_KEY = "logout";

	@Override
	@Transactional
	public ResponseEntity<?> signUp(SignUp signUpRequest) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByEmail(signUpRequest.email());

		if (user != null) {
			return response.fail("이미 가입한 이메일입니다.", HttpStatus.BAD_REQUEST);
		}

		// TODO: user code 제작해야함
		User newUser = User.builder()
				.name(signUpRequest.name())
				.email(signUpRequest.email())
				.password(passwordEncoder.encode(signUpRequest.password()))
				.role(RoleType.USER.getRole())
				.sidoCode(signUpRequest.sidoCode())
				.gugunCode(signUpRequest.gunguCode())
				.phone(signUpRequest.phone())
				.comment("")
				.userCode(userCodeGenerator.generateUserCode(signUpRequest.email()))
				.profileImagePath("")
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
		
		Authentication authentication = tokenProvider.getAuthentication(reissueRequest.accessToken());
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
		
		if(!ObjectUtils.isEmpty(refreshToken)) {
			redisTemplate.delete("RT:" + name);
		}
		
		Long expiration = tokenProvider.getExpiration(logoutRequest.accessToken());
		redisTemplate.opsForValue()
			.set(logoutRequest.accessToken(), LOGOUT_KEY, expiration, TimeUnit.MILLISECONDS);
		
		return response.success("로그아웃 되었습니다.");
	}

	@Transactional(readOnly=true)
	@Override
	public ResponseEntity<?> getUserInfo(String userEmail) {		
		UserInfo userInfo = userMapper.selectUserInfoByEmail(userEmail);
		UserResponse.UserInfo userInfoResponse = UserResponse.UserInfo.from(userInfo);
		
		return response.success(userInfoResponse, "유저 조회 성공", HttpStatus.OK);
	}

	@Transactional
	@Override
	public ResponseEntity<?> updateUserInfo(Update userInfo, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		
		try {
			user.modifySidoCode(userInfo.sidoCode());
			user.modifyGugunCode(userInfo.gunguCode());
			user.modifyComment(userInfo.comment());
			user.modifyName(userInfo.name());
			user.modifyUserPhone(userInfo.phone());
			
			userMapper.updateUser(user);
		} catch (IllegalArgumentException e) {
			return response.fail("잘못된 입력입니다.", HttpStatus.BAD_REQUEST);
		}
		
		return response.success("정보 갱신에 성공했습니다.");
	}

	@Transactional
	@Override
	public ResponseEntity<?> updateUserPassword(PasswordUpdate passwordData, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);
		String currentPassword = passwordData.currentPassword();
		
		if(!passwordEncoder.matches(currentPassword, user.getPassword())) {
			return response.fail("잘못된 패스워드입니다.", HttpStatus.BAD_REQUEST);
		}
		
		user.modifyPassword(passwordEncoder.encode(passwordData.newPassword()));
		userMapper.updatePassword(user);
		
		return response.success("패스워드 변경에 성공했습니다.");
	}

	@Override
	public ResponseEntity<?> findUserPassword(Password passwordForm) {
		User user = userMapper.selectByEmail(passwordForm.email());
		
		if(user == null) {
			return response.fail("존재하지 않는 사용자입니다.", HttpStatus.BAD_REQUEST);
		}
		
		user.modifyPassword(passwordEncoder.encode(passwordForm.email()));
		
		return response.success("패스워드가 초기화되었습니다. 초기 패스워드는 이메일과 동일합니다.");
	}

}
