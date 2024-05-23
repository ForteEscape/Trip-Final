package com.hotplace.vo;

import java.util.List;

import com.hotplace.entity.HotPlaceInfoEntity;
import com.hotplace.entity.HotPlaceReplyEntity;
import com.trip.vo.TripResponse.TripPlanReply;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record HotplaceResponse() {
	
	@Schema(description = "페이징 객체 DTO")
	@Builder
	public static record HotPlacePageInfo(
			@Schema(description = "페이징 오프셋 값입니다.")
			int offset,
			
			@ArraySchema(schema = @Schema(description = "실제 데이터 입니다.", implementation = HotPlaceInfo.class))
			List<HotPlaceInfo> items,
			
			@Schema(description = "전체 데이터 갯수입니다")
			int totalItems) {
		
	}
	
	@Schema(description = "핫 플레이스 데이터")
	@Builder
	public static record HotPlaceInfo(
			@Schema(description = "핫 플레이스 정보 id")
			int id,
			
			@Schema(description = "핫 플레이스 작성자 유저 id")
			int uid,
			
			@Schema(description = "핫 플레이스 이름")
			String title,
			
			@Schema(description = "방문 일자")
			String visitDate,
			
			@Schema(description = "작성자 이름")
			String author,

			@Schema(description = "대표 이미지")
			String representImage,

			@Schema(description = "관광지 분류 코드")
			int contentType,

			@Schema(description = "추천 횟수")
			int recommendCnt,

			@Schema(description = "작성 일자")
			String createdAt) {
		
		public static HotPlaceInfo from(HotPlaceInfoEntity entity) {
			return HotPlaceInfo.builder()
					.id(entity.getId())
					.uid(entity.getUid())
					.title(entity.getTitle())
					.visitDate(entity.getVisitDate())
					.author(entity.getAuthor())
					.representImage(entity.getRepresentImage())
					.contentType(entity.getContentTypeId())
					.recommendCnt(entity.getRecommendCnt())
					.createdAt(entity.getCreatedAt())
					.build();
		}
	}
	
	@Schema(description = "핫 플레이스 상세 DTO")
	@Builder
	public static record HotPlaceDetail(
			@Schema(description = "핫 플레이스 정보 id")
			int id,
			
			@Schema(description = "핫 플레이스 작성자 유저 id")
			int uid,
			
			@Schema(description = "핫 플레이스 이름")
			String title,
			
			@Schema(description = "방문 일자")
			String visitDate,
			
			@Schema(description = "작성자 이름")
			String author,
			
			@Schema(description = "핫 플레이스 설명")
			String placeDesc,
			
			@Schema(description = "이미지 경로 리스트")
			List<String> images,
			
			@Schema(description = "관광지 분류 코드")
			int contentType,

			@Schema(description = "추천 횟수")
			int recommendCnt,

			@Schema(description = "작성 일자")
			String createdAt) {
		
		public static HotPlaceDetail from(HotPlaceInfoEntity entity, List<String> imageList) {
			return HotPlaceDetail.builder()
					.id(entity.getId())
					.uid(entity.getUid())
					.title(entity.getTitle())
					.visitDate(entity.getVisitDate())
					.author(entity.getAuthor())
					.placeDesc(entity.getPlaceDesc())
					.images(imageList)
					.contentType(entity.getContentTypeId())
					.recommendCnt(entity.getRecommendCnt())
					.createdAt(entity.getCreatedAt())
					.build();
		}
	}
	
	@Schema(description = "핫플레이스 댓글")
	@Builder
	public static record HotPlaceReply(
			
			@Schema(description = "댓글 id")
			int id,
			
			@Schema(description = "댓글 작성 유저 id")
			int userId,
			
			@Schema(description = "댓글 작성 유저 이름")
			String author,
			
			@Schema(description = "핫플레이스 id")
			int hotplaceId,
			
			@Schema(description = "내용")
			String content,
			
			@Schema(description = "작성 일자")
			String writeDate,
			
			@Schema(description = "유저 프로필 이미지")
			String userImage) {
		
		public static HotPlaceReply from(HotPlaceReplyEntity entity) {
			return HotPlaceReply.builder()
					.id(entity.getId())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.hotplaceId(entity.getHotplaceId())
					.content(entity.getContent())
					.writeDate(entity.getWriteDate())
					.userImage(entity.getUserImage())
					.build();
		}
	}
}
