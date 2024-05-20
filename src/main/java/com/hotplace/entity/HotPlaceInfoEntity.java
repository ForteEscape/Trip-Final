package com.hotplace.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class HotPlaceInfoEntity implements Comparable<HotPlaceInfoEntity>{
	
	private int id;
	private String title;
	private String author;
	private String visitDate;
	private String placeDesc;
	private int contentTypeId;
	private String createdAt;
	private String modifiedAt;
	private int recommendCnt;
	private String representImage;
	private double recommendWeight;
	
	public void setRecommendWeight(double weight) {
		this.recommendWeight = weight;
	}

	@Override
	public int compareTo(HotPlaceInfoEntity o) {
		return Double.compare(o.recommendWeight, this.recommendWeight);
	}
}
