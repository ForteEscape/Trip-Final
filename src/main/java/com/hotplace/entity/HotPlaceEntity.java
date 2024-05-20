package com.hotplace.entity;

import java.time.LocalDate;

import com.hotplace.vo.HotplaceRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class HotPlaceEntity {
	
	private int id;
	private int userId;
	private int contentId;
	private String hotplaceName;
	private LocalDate visitDate;
	private String placeDesc;
	private int contentTypeId;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	
	public static HotPlaceEntity from(HotplaceRequest.HotPlace hotplace) {
		return HotPlaceEntity.builder()
				.hotplaceName(hotplace.hotplaceName())
				.contentId(hotplace.contentId())
				.visitDate(hotplace.visitDate())
				.placeDesc(hotplace.placeDesc())
				.contentTypeId(hotplace.contentTypeId())
				.build();
	}
}
