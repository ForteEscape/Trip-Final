package com.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "토큰 데이터 VO")
public record Token(
		
		@Schema(description = "엑세스 토큰 데이터")
		String accessToken,
		
		@Schema(description = "리프레시 토큰 데이터")
		String refreshToken,
		
		@Schema(description = "만기 일자, millisecond로 표현되어 있습니다.")
		long accessTokenExpiredTime
) {

}
