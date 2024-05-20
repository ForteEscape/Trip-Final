package com.hotplace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class HotPlaceRecommendEntity {
	
	private int id;
	private int hid;
	private int uid;
	private String recommendTime;
	private int valid;
	private String createdAt;
	private String modifiedAt;
	
}
