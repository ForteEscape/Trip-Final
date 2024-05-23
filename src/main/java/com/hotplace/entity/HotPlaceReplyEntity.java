package com.hotplace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotPlaceReplyEntity {
	
	private int id;
	private int userId;
	private String author;
	private int hotplaceId;
	private String content;
	private String writeDate;
	private String userImage;
	
}
