package com.user.service;

import org.springframework.http.ResponseEntity;

import com.user.vo.UserRequest;
import com.user.vo.UserRequest.Password;
import com.user.vo.UserRequest.PasswordUpdate;

public interface UserService {

	ResponseEntity<?> signUp(UserRequest.SignUp signUpRequest);

	ResponseEntity<?> login(UserRequest.Login loginRequest);

	ResponseEntity<?> reissue(UserRequest.Reissue reissueRequest);

	ResponseEntity<?> logout(UserRequest.Logout logoutRequest);

	ResponseEntity<?> getUserInfo(String userEmail);

	ResponseEntity<?> updateUserInfo(UserRequest.Update userInfo, String userEmail);

	ResponseEntity<?> updateUserPassword(UserRequest.PasswordUpdate passwordData, String userEmail);

	ResponseEntity<?> findUserPassword(Password passwordForm);
}
