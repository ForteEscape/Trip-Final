package com.user.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.UserService;
import com.user.vo.UserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getUserInfo(Principal principal) {
		return userService.getUserInfo(principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping
	public ResponseEntity<?> updateUserInfo(@RequestBody UserRequest.Update userInfo, Principal principal) {
		return userService.updateUserInfo(userInfo, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping("/profile-image")
	public ResponseEntity<?> updateUserProfileImage() {
		return null;
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
}
