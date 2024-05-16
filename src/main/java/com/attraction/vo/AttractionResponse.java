package com.attraction.vo;

import lombok.Builder;

public record AttractionResponse() {

	public static record Sido(int sidoCode, String sidoName) {

		public static Sido from(com.attraction.entity.Sido sido) {
			return new AttractionResponse.Sido(sido.getSidoCode(), sido.getSidoName());
		}

	}

	public static record Gugun(int gugunCode, String gugunName, int sidoCode) {

		public static Gugun from(com.attraction.entity.Gugun gugun) {
			return new AttractionResponse.Gugun(gugun.getGugunCode(), gugun.getGugunName(), gugun.getSidoCode());
		}
	}

	@Builder
	public static record AttractionInfo(int contentId, int contentTypeId, String title, String address, String zipCode,
			String tel, String firstImage, double latitude, double longitude) {

		public static AttractionResponse.AttractionInfo from(com.attraction.entity.AttractionInfo info) {
			return AttractionResponse.AttractionInfo.builder()
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

	@Builder
	public static record AttractionDescription(int contentId, String homepage, String overview, String telname) {

		public static AttractionResponse.AttractionDescription from(
				com.attraction.entity.AttractionDescription description) {
			
			return AttractionResponse.AttractionDescription.builder()
					.contentId(description.getContentId())
					.homepage(description.getHomepage())
					.overview(description.getOverview())
					.telname(description.getTelname())
					.build();
		}
	}
}
