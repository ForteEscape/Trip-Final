package com.attraction.vo;

import lombok.Builder;

@Builder
public record AttractionInfo() {
	
	// TODO: implements
	public static AttractionInfo from(com.attraction.entity.AttractionInfo attractionInfo) {
		return null;
	}
}
