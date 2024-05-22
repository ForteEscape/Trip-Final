package com.hotplace.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotplaceRequest() {
	
	@Schema(description = "핫 플레이스 요청 DTO")
	public static record HotPlace(
			@NotNull(message = "관광지 id는 null일 수 없습니다.")
			@Schema(description = "관광지 ID", example = "123456")
			Integer contentId,
			
			@NotBlank(message = "핫 플레이스 이름은 공백일 수 없습니다.")
			@Schema(description = "관광지 이름", example = "attraction title")
			String hotplaceName,
			
			@NotNull(message = "핫 플레이스 방문 날자는 null일 수 없습니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
			@Schema(description = "관광지 방문 날자", example = "2023-12-31")
			LocalDate visitDate,
			
			@NotNull(message = "관광지 id는 null일 수 없습니다.")
			@Schema(description = "관광지 타입(분류) ID")
			Integer contentTypeId,
			
			@NotBlank(message = "핫 플레이스 설명은 공백일 수 없습니다.")
			@Schema(description = "핫 플레이스 설명")
			String placeDesc) {
		
	}
}
