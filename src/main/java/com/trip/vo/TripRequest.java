package com.trip.vo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record TripRequest() {
	
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	@Schema(description = "검색 필터")
	public static class SearchFilter {
		@Schema(description = "시/도 코드", example = "1")
		int sidoCode;
		
		@Schema(description = "구/군 코드", example = "1")
		int gunguCode;
		
		@Schema(description = "관광지 분류 코드", example = "12")
		int contentType;
		
		@Schema(description = "검색어", example = "공원")
		String keyword;
	}
	
	@Schema(description = "여행 계획 DTO")
	public static record TripPlan(
			
			@NotBlank(message = "계획 이름은 필수 입력입니다.")
			@Schema(description = "계획 이름", example = "test plan")
			String planName,
			
			@NotNull(message = "시작 날자는 필수 입력입니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
			@Schema(description = "시작 날짜", example = "test plan")
			LocalDate startDate,
			
			@NotNull(message = "종료 날자는 필수 입력입니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
			@Schema(description = "종료 날짜", example = "test plan")
			LocalDate endDate,
			
			@NotNull(message = "여행지는 null일 수 없습니다.")
			@Schema(description = "여행 일정")
			List<@NotNull(message = "여행 일정 구성 요소는 null일 수 없습니다.") List<@NotNull(message="여행지 id는 필수요소입니다.") Integer>> places,
			
			@NotNull(message = "멤버는 null일 수 없습니다.")
			@Schema(description = "참여 맴버")
			List<@NotNull(message = "멤버 코드는 null일 수 없습니다.") String> members
			
	) {
		
	}
	
	@Schema(description = "댓글 DTO")
	public static record TripReply(
			@NotBlank(message = "댓글 내용은 비어 있을 수 없습니다.") 
			@Schema(description = "내용 데이터", example = "test reply")
			String content) {
		
	}

}
