package com.hotplace.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HotPlaceReplyEntity {
	
	private int id;
	private int userId;
	private String author;
	private int hotplaceId;
	private String content;
	private String writeDate;
	private String userImage;
	
}
