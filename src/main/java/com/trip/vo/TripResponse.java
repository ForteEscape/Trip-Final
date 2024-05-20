package com.trip.vo;

import java.util.List;

import com.trip.entity.TripPlanEntity;

import lombok.Builder;

public record TripResponse() {
	
	@Builder
	public static record AttractionInfo(int contentId, int contentTypeId, String title, String address, String zipCode,
			String tel, String firstImage, double latitude, double longitude) {

		public static TripResponse.AttractionInfo from(com.attraction.entity.AttractionInfo info) {
			return TripResponse.AttractionInfo.builder()
					.contentId(info.getContentId())
					.contentTypeId(info.getContentTypeId())
					.title(info.getTitle())
					.address((info.getAddr1() + " " + info.getAddr2()).trim())
					.zipCode(info.getZipCode())
					.tel(info.getTel())
					.firstImage(info.getFirstImage())
					.latitude(info.getLatitude())
					.longitude(info.getLongitude())
					.build();
		}

	}

	@Builder
	public static record AttractionDescription(int contentId, String homepage, String overview, String telname) {

		public static TripResponse.AttractionDescription from(
				com.attraction.entity.AttractionDescription description) {
			
			return TripResponse.AttractionDescription.builder()
					.contentId(description.getContentId())
					.homepage(description.getHomepage())
					.overview(description.getOverview())
					.telname(description.getTelname())
					.build();
		}
	}
	
	public static record TripPlanResponse(
			List<TripPlan> selectedTrip,
			List<TripPlan> unSelectedTrip
			) {
		
	}
	
	@Builder
	public static record TripPlan(
			int id,
			String planName,
			String startDate,
			String endDate,
			int userId,
			String author,
			boolean isSelected) {
		
		public static TripPlan from(TripPlanEntity entity, boolean isSelected) {
			return TripPlan.builder()
					.id(entity.getId())
					.planName(entity.getPlanName())
					.startDate(entity.getStartDate().toString())
					.endDate(entity.getEndDate().toString())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.isSelected(isSelected)
					.build();
		}
	}
	
	@Builder
	public static record TripPlanDetail(
			int id,
			String planName,
			String startDate,
			String endDate,
			int userId,
			String author,
			List<String> members
			) {
		
	}
}
