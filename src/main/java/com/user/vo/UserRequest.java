package com.user.vo;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequest() {
	
	@Schema(description = "이메일 검증 DTO")
	public static record EmailValidate(
			@NotBlank(message = "이름은 필수 입력입니다.")
			@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
			@Schema(description = "가입 이메일", example = "example@naver.com")
			String email) {

	}

	@Schema(description = "이메일 찾기에 필요한 정보 DTO")
	public static record Email(
			
			@NotBlank(message = "이름은 필수 입력입니다.")
			@Schema(description = "이름", example = "John Doe")
			String name,
			
			@NotBlank(message = "전화번호는 필수 입력입니다.")
			@Size(min=13, max=13, message="전화번호 양식에 맞게 다시 적어주세요")
			@Schema(description = "전화번호는 010-1234-5678과 같은 양식으로 적어주세요", example = "010-1234-5678")
			String phone) {
		
	}
	
	@Schema(description = "비밀번호 찾기(변경)에 필요한 정보 DTO")
	public static record Password(
			
			@NotBlank(message = "이름은 필수 입력입니다.")
			@Schema(description = "이름", example = "John Doe")
			String name,
			
			@NotBlank(message = "이메일은 필수 입력입니다.")
			@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
			@Schema(description = "가입 이메일", example = "example@naver.com")
			String email) {
		
	}
	
	@Schema(description = "비밀번호 변경 DTO")
	public static record PasswordUpdate(
			
			@NotBlank(message = "현재 비밀번호는 필수 입력값입니다.")
			@Schema(description = "현재 비밀번호", example = "testPassword123!!")
			String currentPassword,
			
			@NotBlank(message = "새로운 비밀번호는 필수 입력값입니다.")
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 사용되어야 합니다.")
			@Schema(description = "새로운 비밀번호, 비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 사용되어야 합니다.", example = "newTestPassword123!!")
			String newPassword
	) {
		
	}
	
	@Schema(description = "회원 정보 수정 DTO")
	public static record Update(
			
			@NotBlank(message = "이름은 필수 입력입니다.")
			@Schema(description = "이름", example = "John Doe")
			String name,
			
			@NotNull(message = "시 도 주소는 필수 입력값입니다.")
			@Schema(description = "거주하는 시/도 코드 데이터", example = "1")
			Integer sidoCode,
			
			@NotNull(message = "군 구 주소는 필수 입력값입니다.")
			@Schema(description = "거주하는 군/구 코드 데이터", example = "1")
			Integer gunguCode,
			
			@Schema(description = "가입 이메일", example = "example@naver.com")
			String comment,
			
			@NotBlank(message = "전화번호는 필수 입력값입니다.")
			@Size(min=13, max=13, message="전화번호 양식에 맞게 다시 적어주세요")
			@Schema(description = "전화번호는 010-1234-5678과 같은 양식으로 적어주세요", example = "010-1234-5678")
			String phone) {
		
	}

	@Schema(description = "회원 가입 신청 DTO")
	public static record SignUp(

			@NotBlank(message = "이메일은 필수 입력값입니다.") 
			@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.") 
			@Schema(description = "가입 이메일", example = "example@naver.com")
			String email,

			@NotBlank(message = "비밀번호는 필수 입력값입니다.") 
			@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 사용되어야 합니다.") 
			@Schema(description = "비밀번호, 비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자가 사용되어야 합니다.", example = "testPassword123!!")
			String password,
			
			@NotBlank(message = "전화번호는 필수 입력값입니다.")
			@Size(min=13, max=13, message="전화번호 양식에 맞게 다시 적어주세요")
			@Schema(description = "전화번호는 010-1234-5678과 같은 양식으로 적어주세요", example = "010-1234-5678")
			String phone,

			@NotBlank(message = "이름은 필수 입력값입니다.") 
			@Schema(description = "이름", example = "John Doe")
			String name,

			@NotNull(message = "시 도 주소는 필수 입력값입니다.") 
			@Schema(description = "거주하는 시/도 코드 데이터", example = "1")
			Integer sidoCode,

			@NotNull(message = "군 구 주소는 필수 입력값입니다.") 
			@Schema(description = "거주하는 군/구 코드 데이터", example = "1")
			Integer gunguCode) {

	}

	@Schema(description = "로그인 DTO")
	public static record Login(

			@NotBlank(message = "이메일은 필수 입력값입니다.") 
			@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.") 
			@Schema(description = "로그인 이메일", example = "test@naver.com")
			String email,

			@NotBlank(message = "비밀번호는 필수 입력값입니다.") 
			@Schema(description = "로그인 패스워드", example = "testPassword123!!")
			String password) {

		public UsernamePasswordAuthenticationToken toAuthentication() {
			return new UsernamePasswordAuthenticationToken(email, password);
		}
	}

	@Schema(description = "리프레시 토큰으로 엑세스 토큰 재발급을 위한 DTO")
	public static record Reissue(

			@NotBlank(message = "accessToken은 필수 값입니다.") 
			@Schema(description = "엑세스 토큰")
			String accessToken,

			@NotBlank(message = "refreshToken은 필수 값입니다.")
			@Schema(description = "리프레시 토큰")
			String refreshToken) {

	}

	@Schema(description = "로그아웃 DTO")
	public static record Logout(

			@NotBlank(message = "잘못된 요청입니다.") 
			@Schema(description = "엑세스 토큰")
			String accessToken,

			@NotBlank(message = "잘못된 요청입니다.") 
			@Schema(description = "리프레시 토큰")
			String refreshToken) {

	}
}
