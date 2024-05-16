package com.attraction.service;

import org.springframework.http.ResponseEntity;

public interface AttractionService {
	
	ResponseEntity<?> getAreaInfo();
	
	ResponseEntity<?> getAttractionInfo();
}
