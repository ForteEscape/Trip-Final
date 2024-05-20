package com.hotplace.vo;

import java.util.List;

import com.hotplace.entity.HotPlaceInfoEntity;

import lombok.Builder;

public record HotplaceResponse() {
	
	@Builder
	public static record PageInfo(
			int offset,
			List<HotPlaceInfo> items,
			int totalItems) {
		
	}
	
	@Builder
	public static record HotPlaceInfo(
			int id,
			int uid,
			String title,
			String visitDate,
			String author,
			String representImage,
			int contentType,
			int recommendCnt,
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
	
	@Builder
	public static record HotPlaceDetail(
			int id,
			int uid,
			String title,
			String visitDate,
			String author,
			String placeDesc,
			List<String> images,
			int contentType,
			int recommendCnt,
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
}
