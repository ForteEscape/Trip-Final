package com.user.controller;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.dto.Response;
import com.user.service.UserService;
import com.user.vo.UserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	private final UserService userService;
	private final Response response;
	
	@Value("${file.path.upload.image.user-profile}")
	private String uploadProfileImagePath;
	
	@GetMapping("/test")
	public ResponseEntity<?> testControll() {
		return response.success("통신 성공");
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getUserInfo(Principal principal) {
		return userService.getUserInfo(principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping
	public ResponseEntity<?> updateUserInfo(@RequestBody UserRequest.Update userInfo,  Principal principal) {
		
		return userService.updateUserInfo(userInfo, principal.getName(), "");
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/password")
	public ResponseEntity<?> updateUserPassword(@RequestBody UserRequest.PasswordUpdate passwordData, Principal principal) {
		return userService.updateUserPassword(passwordData, principal.getName());
	}
	
	@PostMapping("/password")
	public ResponseEntity<?> findUserPassword(@RequestBody UserRequest.Password passwordForm) {
		return userService.findUserPassword(passwordForm);
	}
	
	@PostMapping("/email")
	public ResponseEntity<?> findUserEmail(@RequestBody UserRequest.Email emailForm) {
		return userService.findUserEmail(emailForm);
	}
}
