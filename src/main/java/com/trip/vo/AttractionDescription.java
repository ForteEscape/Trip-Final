package com.attraction.vo;

import lombok.Builder;

@Builder
public record AttractionDescription() {

	public static AttractionDescription from(com.attraction.entity.AttractionDescription desc) {
		return null;
	}
}
