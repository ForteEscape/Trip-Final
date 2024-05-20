package com.trip.vo;

import lombok.Builder;

@Builder
public record AttractionInfo(
		int contentId, 
		int contentTypeId, 
		String title,
		String address, 
		String zipCode,
		String tel, 
		String firstImage, 
		double latitude, 
		double longitude) {
	
	// TODO: implements
	public static AttractionInfo from(com.attraction.entity.AttractionInfo info) {
		return AttractionInfo.builder()
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
