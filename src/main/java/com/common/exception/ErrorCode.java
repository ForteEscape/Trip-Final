package com.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 유저가 없습니다.")
	;
	
	private final HttpStatus httpStatus;
	private final String errorMessage;
}
