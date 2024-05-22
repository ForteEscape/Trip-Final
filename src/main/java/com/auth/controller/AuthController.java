package com.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.vo.Token;
import com.common.dto.Response;
import com.common.util.Helper;
import com.user.service.UserService;
import com.user.vo.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "인증 API", description = "인증 API")
public class AuthController {
	
	private final UserService userService;
	private final Response response;
	
	@Operation(summary = "회원 가입", description = "회원 가입 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "회원 가입 성공"),
			@ApiResponse(responseCode = "400", description = "입력 데이터가 부정확하거나 이미 존재하는 회원입니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Valid @RequestBody UserRequest.SignUp signUp, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.signUp(signUp);
	}
	
	@Operation(summary = "로그인", description = "로그인 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그인 성공", content = {
					@Content(schema = @Schema(implementation = Token.class))
			}),
			@ApiResponse(responseCode = "400", description = "로그인 실패"),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/login")
	public ResponseEntity<?> login(@Validated @RequestBody UserRequest.Login login, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}	
		
		return userService.login(login);
	}
	
	@Operation(summary = "갱신", description = "엑세스 토큰 갱신 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "갱신 성공", content = {
					@Content(schema = @Schema(implementation = Token.class))
			}),
			@ApiResponse(responseCode = "400", description = "갱신 실패"),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@Validated @RequestBody UserRequest.Reissue reissue, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.reissue(reissue);
	}
	
	@Operation(summary = "로그아웃", description = "로그아웃 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
			@ApiResponse(responseCode = "400", description = "로그아웃 실패"),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@Validated @RequestBody UserRequest.Logout logout, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.logout(logout);
	}
	
	@Operation(summary = "중복 이메일 검증", description = "중복 이메일 검증 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "사용 가능한 이메일입니다."),
			@ApiResponse(responseCode = "400", description = "중복 이메일이 존재합니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/validate/email")
	public ResponseEntity<?> validateDuplicateEmail(@Validated @RequestBody UserRequest.EmailValidate email, Errors errors) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return userService.validateEmail(email);
	}
}
