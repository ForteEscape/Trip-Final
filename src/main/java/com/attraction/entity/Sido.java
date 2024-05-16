package com.attraction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sido {
	
	private int sidoCode;
	private String sidoName;
	
	// TODO: implements
	public static Sido from(com.attraction.vo.Sido sido) {
		
		return null;
	}
}
