package com.notice.vo;

import jakarta.validation.constraints.NotBlank;

public record NoticeRequest() {
	
	public static record NoticeData(
			
			@NotBlank(message = "제목은 비어있을 수 없습니다.")
			String title,
			
			@NotBlank(message = "내용은 비어있을 수 없습니다.")
			String content) {
		
	}
	
	public static record ModifiedNotice(
			
			@NotBlank(message = "제목은 비어있을 수 없습니다.")
			String title,
			
			@NotBlank(message = "내용은 비어있을 수 없습니다.")
			String content) {
		
	}

}
