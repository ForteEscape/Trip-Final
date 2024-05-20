package com.hotplace.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotplaceRequest() {
	
	public static record HotPlace(
			@NotNull(message = "관광지 id는 null일 수 없습니다.")
			Integer contentId,
			
			@NotBlank(message = "핫 플레이스 이름은 공백일 수 없습니다.")
			String hotplaceName,
			
			@NotNull(message = "핫 플레이스 방문 날자는 null일 수 없습니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
			LocalDate visitDate,
			
			@NotNull(message = "관광지 id는 null일 수 없습니다.")
			Integer contentTypeId,
			
			@NotBlank(message = "핫 플레이스 설명은 공백일 수 없습니다.")
			String placeDesc) {
		
	}
}
