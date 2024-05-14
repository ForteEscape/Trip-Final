package com.common.dto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.Builder;

@Component
public record Response() {

	@Builder
	private static record Body(int state, String result, String message, Object data, Object error) {

	}
	
	public ResponseEntity<?> success(Object data, String message, HttpStatus httpStatus) {
		Body body = Body.builder()
				.state(httpStatus.value())
				.data(data)
				.result("success")
				.message(message)
				.error(Collections.emptyList())
				.build();
		
		return ResponseEntity.ok(body);
	}
	
	// message만 가진 성공 응답 객체를 반환한다.
	public ResponseEntity<?> success(String message) {
		return success(Collections.emptyList(), message, HttpStatus.OK);
	}
	
	// data만 가진 성공 응답 객체를 반환한다.
	public ResponseEntity<?> success(Object data) {
		return success(data, null, HttpStatus.OK);
	}
	
	public ResponseEntity<?> fail(Object data, String message, HttpStatus httpStatus) {
		Body body = Body.builder()
				.data(data)
				.result("fail")
				.message(message)
				.state(httpStatus.value())
				.error(Collections.emptyList())
				.build();
		
		return ResponseEntity.ok(body);
	}
	
	public ResponseEntity<?> fail(String message, HttpStatus status) {
		return fail(null, message, status);
	}
	
	public ResponseEntity<?> invalidFields(LinkedList<LinkedHashMap<String, String>> errors) {
		Body body = Body.builder()
				.state(HttpStatus.BAD_REQUEST.value())
				.data(Collections.emptyList())
				.result("fail")
				.message("")
				.error(errors)
				.build();
		
		return ResponseEntity.ok(body);
	}
}
