package com.user.controller;

import java.security.Principal;

import org.springframework.http.MediaType;
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

import com.user.service.UserService;
import com.user.vo.UserRequest;
import com.user.vo.UserResponse.UserInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "사용자 API", description = "사용자 정보 api")
public class UserController {
	
	private final UserService userService;
	
	@Operation(summary = "유저 정보 조회", description = "유저 정보 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 정보 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  UserInfo.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<?> getUserInfo(Principal principal) {
		return userService.getUserInfo(principal.getName());
	}
	
	@Operation(summary = "유저 정보 수정", description = "유저 정보 수정 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 정보 수정 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUserInfo(@RequestPart("profile") MultipartFile image,
			@RequestPart("userInfo") UserRequest.Update userInfo,  Principal principal) {
		
		return userService.updateUserInfo(image, userInfo, principal.getName());
	}
	
	@Operation(summary = "유저 패스워드 변경", description = "유저 패스워드 변경 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 패스워드 변경 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PutMapping("/password")
	public ResponseEntity<?> updateUserPassword(@RequestBody UserRequest.PasswordUpdate passwordData, Principal principal) {
		return userService.updateUserPassword(passwordData, principal.getName());
	}
	
	@Operation(summary = "유저 패스워드 찾기", description = "유저 패스워드 검색 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 패스워드 검색 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/password")
	public ResponseEntity<?> findUserPassword(@RequestBody UserRequest.Password passwordForm) {
		return userService.findUserPassword(passwordForm);
	}
	
	@Operation(summary = "유저 이메일 찾기", description = "유저 이메일 검색 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 이메일 검색 성공", content = {
					@Content(schema = @Schema(description = "결과 이메일", type = "String"))
			}),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PostMapping("/email")
	public ResponseEntity<?> findUserEmail(@RequestBody UserRequest.Email emailForm) {
		return userService.findUserEmail(emailForm);
	}
	
	@Operation(summary = "유저 수 조회", description = "유저 수 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "유저 수 검색 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/count")
	public ResponseEntity<?> getUserTotalCount() {
		return userService.countAllUser();
	}
}
