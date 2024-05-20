package com.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당하는 유저가 없습니다."),
	INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "파일 확장자가 잘못되었습니다."),
	IO_EXCEPTION_ON_IMAGE_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "업로드 도중 오류가 발생했습니다."),
	NO_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "파일 확장자가 존재하지 않습니다."),
	PUT_OBJECT_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "S3 업로드 도중 오류가 발생했습니다."), 
	IO_EXCEPTION_ON_IMAGE_DELETE(HttpStatus.INTERNAL_SERVER_ERROR, "S3 데이터 삭제 중 오류가 발생했습니다."), 
	INVALID_START_DATE(HttpStatus.BAD_REQUEST, "시작 날자는 오늘보다 이전일 수 없습니다."),
	INVALID_END_DATE(HttpStatus.BAD_REQUEST, "잘못된 종료 날자입니다. 종료 날자는 시작 날자보다 이전이거나, 오늘보다 이전일 수 없습니다."), 
	MY_BATIS_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "MyBaits와 통신 중 문제가 발생했습니다.")
	;
	
	private final HttpStatus httpStatus;
	private final String errorMessage;
}
