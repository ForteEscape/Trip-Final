package com.trip.entity;

import java.time.LocalDate;

import com.trip.vo.TripRequest.TripPlan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class TripPlanEntity {
	
	@Setter
	private int id;
	private String planName;
	private LocalDate startDate;
	private LocalDate endDate;
	private int userId;
	private String author;
	
	public static TripPlanEntity from(TripPlan plan) {
		return TripPlanEntity.builder()
				.planName(plan.planName())
				.startDate(plan.startDate())
				.endDate(plan.endDate())
				.build();
	}
}
