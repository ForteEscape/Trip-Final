package com.attraction.vo;

import com.attraction.entity.AttractionInfo;

import io.swagger.v3.oas.annotations.media.Schema;

public record AttractionResponse() {

	@Schema(description = "시/도 데이터")
	public static record Sido(
			@Schema(description = "시/도 코드")
			int sidoCode, 
			
			@Schema(description = "시/도 이름")
			String sidoName) {

		public static Sido from(com.attraction.entity.Sido sido) {
			return new AttractionResponse.Sido(sido.getSidoCode(), sido.getSidoName());
		}

	}

	@Schema(description = "군/구 데이터")
	public static record Gugun(
			@Schema(description = "군/구 코드")
			int gugunCode, 
			
			@Schema(description = "군/구 이름")
			String gugunName, 
			
			@Schema(description = "군/구가 소속된 시/도의 코드")
			int sidoCode) {

		public static Gugun from(com.attraction.entity.Gugun gugun) {
			return new AttractionResponse.Gugun(gugun.getGugunCode(), gugun.getGugunName(), gugun.getSidoCode());
		}
	}

	@Schema(description = "간단한 관광지 이름/위치 데이터")
	public static record SimpleAttractionInfo(
			String title,
			double latitude,
			double longitude) {
		
		public static SimpleAttractionInfo from(AttractionInfo info) {
			return new SimpleAttractionInfo(info.getTitle(), info.getLatitude(), info.getLongitude());
		}
	}
}
