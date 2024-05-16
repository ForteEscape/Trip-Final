package com.attraction.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AttractionInfo {
	
	private int contentId;
	private int sidoCode;
	private int gugunCode;
	private int contentTypeId;
	private String title;
	private String addr1;
	private String addr2;
	private String zipCode;
	private String tel;
	private String firstImage;
	private double latitude;
	private double longitude;
	
}
