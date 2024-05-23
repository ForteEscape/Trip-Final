package com.attraction.service;

import org.springframework.http.ResponseEntity;

public interface AttractionQueryService {
	
	ResponseEntity<?> selectAllCity();
	
	ResponseEntity<?> selectGugunBySidoId(String sidoId);
	
	ResponseEntity<?> selectByContentId(String contentId);
	
	ResponseEntity<?> getAttractionCount();
}
