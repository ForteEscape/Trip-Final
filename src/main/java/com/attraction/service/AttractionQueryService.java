package com.attraction.service;

import org.springframework.http.ResponseEntity;

public interface AttractionQueryService {
	
	ResponseEntity<?> selectAllCity();
	
	ResponseEntity<?> selectGugunBySidoId(String sidoId);
}
