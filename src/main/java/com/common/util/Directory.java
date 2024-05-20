package com.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Directory {
	PROFILE("profile"),
	HOTPLACE("hotplace"),
	NOTICE("notice"),
	TRIPPLAN("trip-plan");
	
	private final String value;
}
