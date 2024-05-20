package com.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.dto.Response;
import com.common.util.Helper;
import com.user.service.UserService;
import com.user.vo.UserRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;
	private final Response response;
	
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserRequest.SignUp signUp, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.signUp(signUp);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody UserRequest.Login login, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}	
		
		return userService.login(login);
	}
	
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@Validated @RequestBody UserRequest.Reissue reissue, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.reissue(reissue);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Validated @RequestBody UserRequest.Logout logout, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.logout(logout);
	}
	
	@PostMapping("/validate/email")
	public ResponseEntity<?> validateDuplicateEmail(@Validated @RequestBody UserRequest.EmailValidate email, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.validateEmail(email);
	}
}
