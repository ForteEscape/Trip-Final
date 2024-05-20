package com.trip.vo;

import lombok.Builder;

@Builder
public record AttractionDescription(int contentId, String homepage, String overview, String telname) {

	public static AttractionDescription from(com.attraction.entity.AttractionDescription description) {
		return AttractionDescription.builder()
				.contentId(description.getContentId())
				.homepage(description.getHomepage())
				.overview(description.getOverview())
				.telname(description.getTelname())
				.build();
	}
}
