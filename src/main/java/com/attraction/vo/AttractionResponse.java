package com.attraction.vo;

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

}
