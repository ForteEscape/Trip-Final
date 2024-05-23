package com.trip.vo;

import java.util.List;

import com.trip.entity.TripPlanEntity;
import com.trip.entity.TripPlanReplyEntity;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record TripResponse() {
	
	@Builder
	@Schema(description = "관광지 정보")
	public static record AttractionInfo(
			@Schema(description = "관광지 id", example = "12345")
			int contentId, 
			
			@Schema(description = "관광지 분류 코드", example = "12")
			int contentTypeId,
			
			@Schema(description = "관광지 이름", example = "남산타워")
			String title,
			
			@Schema(description = "주소", example = "서울시 관악구 1234-5")
			String address,
			
			@Schema(description = "우편 번호", example = "12345")
			String zipCode,
			
			@Schema(description = "전화 번호", example = "010-1234-5678")
			String tel, 
			
			@Schema(description = "대표 이미지")
			String firstImage, 
			
			@Schema(description = "위도", example = "37.123456")
			double latitude, 
			
			@Schema(description = "경도", example = "127.123456")
			double longitude) {

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
	@Schema(description = "관광지 상세 정보")
	public static record AttractionDescription(
			
			@Schema(description = "관광지 id", example = "12345")
			int contentId,
			
			@Schema(description = "홈페이지")
			String homepage,
			
			@Schema(description = "관광지 설명")
			String overview,
			
			@Schema(description = "전화번호")
			String telname) {

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
	
	@Schema(description = "여행 계획 응답 DTO")
	public static record TripPlanResponse(
			
			@ArraySchema(schema = @Schema(description = "선택한 여행 리스트", implementation = TripPlan.class))
			List<TripPlan> selectedTrip,
			
			@ArraySchema(schema = @Schema(description = "선택하지 않은 여행 리스트", implementation = TripPlan.class))
			List<TripPlan> unSelectedTrip) {
		
	}
	
	
	@Builder
	@Schema(description = "여행 계획 정보")
	public static record TripPlan(
			
			@Schema(description = "여행 계획 id")
			int id,
			
			@Schema(description = "여행 계획 이름")
			String planName,
			
			@Schema(description = "출발 날자")
			String startDate,
			
			@Schema(description = "종료 날자")
			String endDate,
			
			@Schema(description = "작성자 유저 id")
			int userId,
			
			@Schema(description = "작성자 이름")
			String author,
			
			@Schema(description = "선택되었는지")
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
	@Schema(description = "여행 계획 상세 정보")
	public static record TripPlanDetail(
			
			@Schema(description = "여행 계획 id")
			int id,
			
			@Schema(description = "여행 계획 이름")
			String planName,
			
			@Schema(description = "출발 날자")
			String startDate,
			
			@Schema(description = "종료 날자")
			String endDate,
			
			@Schema(description = "작성자 유저 id")
			int userId,
			
			@Schema(description = "작성자 이름")
			String author,
			
			@ArraySchema(schema = @Schema(description = "참여 맴버 이메일"))
			List<String> members,
			
			@ArraySchema(schema = @Schema(description = "여행 경로(방문지)"))
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
	
	@Schema(description = "여행 계획 댓글")
	@Builder
	public static record TripPlanReply(
			
			@Schema(description = "댓글 id")
			int id,
			
			@Schema(description = "댓글 작성 유저 id")
			int userId,
			
			@Schema(description = "댓글 작성 유저 이름")
			String author,
			
			@Schema(description = "계획 id")
			int planId,
			
			@Schema(description = "내용")
			String content,
			
			@Schema(description = "작성 일자")
			String writeDate,
			
			@Schema(description = "유저 프로필 이미지")
			String userImage) {
		
		public static TripPlanReply from(TripPlanReplyEntity entity) {
			return TripPlanReply.builder()
					.id(entity.getId())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.planId(entity.getPlanId())
					.content(entity.getContent())
					.writeDate(entity.getWriteDate())
					.userImage(entity.getUserImage())
					.build();
		}
	}
}
