package com.trip.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TripPlanReplyEntity {
	
	private int id;
	private int userId;
	private String author;
	private int planId;
	private String content;
	private String writeDate;
	private String userImage;
	
}
