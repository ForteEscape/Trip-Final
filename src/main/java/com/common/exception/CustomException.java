package com.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class CustomException extends RuntimeException {
	
	private HttpStatus status;
	private String message;
	
	public CustomException(ErrorCode errorCode) {
		this.status = errorCode.getHttpStatus();
		this.message = errorCode.getErrorMessage();
	}
}
