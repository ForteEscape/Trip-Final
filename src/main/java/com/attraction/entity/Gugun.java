package com.attraction.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Gugun {
	
	private int gugunCode;
	private String gugunName;
	private int sidoCode;
	
	public static Gugun from(com.attraction.vo.Gugun gugun) {
		return null;
	}
}
