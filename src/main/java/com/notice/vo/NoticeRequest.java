package com.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record NoticeRequest() {
	
	@Schema(description = "공지사항 DTO")
	public static record NoticeData(
			
			@NotBlank(message = "제목은 비어있을 수 없습니다.")
			@Schema(description = "제목", example = "테스트 공지 제목")
			String title,
			
			@NotBlank(message = "내용은 비어있을 수 없습니다.")
			@Schema(description = "내용", example = "테스트 공지 내용")
			String content) {
		
	}
	
	@Schema(description = "공지사항 수정 DTO")
	public static record ModifiedNotice(
			
			@NotBlank(message = "제목은 비어있을 수 없습니다.")
			@Schema(description = "제목", example = "테스트 공지 수정 제목")
			String title,
			
			@NotBlank(message = "내용은 비어있을 수 없습니다.")
			@Schema(description = "내용", example = "테스트 공지 수정 내용")
			String content) {
		
	}

}
