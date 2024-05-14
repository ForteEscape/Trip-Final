package com.user.vo;

import java.util.List;

import com.auth.vo.Token;

import lombok.Builder;

@Builder
public record UserResponse(
		int stateCode,
		String result,
		String message,
		Token token,
		List<String> error
		){

}
