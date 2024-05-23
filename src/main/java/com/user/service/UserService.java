package com.user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.user.vo.UserRequest;
import com.user.vo.UserRequest.Email;
import com.user.vo.UserRequest.EmailValidate;
import com.user.vo.UserRequest.Password;

public interface UserService {

	ResponseEntity<?> signUp(UserRequest.SignUp signUpRequest);

	ResponseEntity<?> login(UserRequest.Login loginRequest);

	ResponseEntity<?> reissue(UserRequest.Reissue reissueRequest);

	ResponseEntity<?> logout(UserRequest.Logout logoutRequest);

	ResponseEntity<?> getUserInfo(String userEmail);

	ResponseEntity<?> updateUserInfo(MultipartFile image, UserRequest.Update userInfo, String userEmail);

	ResponseEntity<?> updateUserPassword(UserRequest.PasswordUpdate passwordData, String userEmail);

	ResponseEntity<?> findUserPassword(Password passwordForm);

	ResponseEntity<?> findUserEmail(Email emailForm);

	ResponseEntity<?> validateEmail(EmailValidate email);

	ResponseEntity<?> countAllUser();
}
