package com.trip.vo;

import java.util.List;

import com.trip.entity.TripPlanEntity;
import com.trip.entity.TripPlanReplyEntity;

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
			List<String> members,
			List<List<TripResponse.AttractionInfo>> attractions) {
		
		public static TripPlanDetail from(TripPlanEntity entity, 
				List<List<TripResponse.AttractionInfo>> places, List<String> members) {
			
			return TripPlanDetail.builder()
					.id(entity.getId())
					.planName(entity.getPlanName())
					.startDate(entity.getStartDate().toString())
					.endDate(entity.getEndDate().toString())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.members(members)
					.attractions(places)
					.build();
		}
	}
	
	@Builder
	public static record TripPlanReply(
			int id,
			int userId,
			String author,
			int planId,
			String content,
			String writeDate) {
		
		public static TripPlanReply from(TripPlanReplyEntity entity) {
			return TripPlanReply.builder()
					.id(entity.getId())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.planId(entity.getPlanId())
					.content(entity.getContent())
					.writeDate(entity.getWriteDate())
					.build();
		}
	}
}
