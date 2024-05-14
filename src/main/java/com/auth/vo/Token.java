package com.auth.vo;

public record Token(
		String accessToken,
		String refreshToken,
		long accessTokenExpiredTime
) {

}
