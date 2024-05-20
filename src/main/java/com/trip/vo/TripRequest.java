package com.trip.vo;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	public static class SearchFilter {
		int sidoCode;
		int gunguCode;
		int contentType;
		String keyword;
	}
	
	public static record TripPlan(
			
			@NotBlank(message = "계획 이름은 필수 입력입니다.")
			String planName,
			
			@NotNull(message = "시작 날자는 필수 입력입니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
			LocalDate startDate,
			
			@NotNull(message = "종료 날자는 필수 입력입니다.")
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
			LocalDate endDate,
			
			@NotNull(message = "여행지는 null일 수 없습니다.")
			List<@NotNull(message = "여행 일정 구성 요소는 null일 수 없습니다.") List<@NotNull(message="여행지 id는 필수요소입니다.") Integer>> places,
			
			@NotNull(message = "멤버는 null일 수 없습니다.")
			List<@NotNull(message = "멤버 코드는 null일 수 없습니다.") String> members
			
	) {
		
	}

}
