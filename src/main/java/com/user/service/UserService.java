package com.user.service;

import org.springframework.http.ResponseEntity;

import com.user.vo.UserRequest;

public interface UserService {

	ResponseEntity<?> signUp(UserRequest.SignUp signUpRequest);

	ResponseEntity<?> login(UserRequest.Login loginRequest);

	ResponseEntity<?> reissue(UserRequest.Reissue reissueRequest);

	ResponseEntity<?> logout(UserRequest.Logout logoutRequest);
}
