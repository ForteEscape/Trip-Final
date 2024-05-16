package com.attraction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attraction.service.AttractionQueryService;
import com.attraction.service.AttractionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/attraction")
public class AttractionController {

	private final AttractionService attractionService;
	private final AttractionQueryService attractionQueryService;

	// sido 데이터 업데이트
	// gugun 데이터 업데이트
	@GetMapping("/area")
	public ResponseEntity<?> updateSido() {
		return attractionService.getAreaInfo();
	}

	// attraction info 데이터 업데이트
	@GetMapping
	public ResponseEntity<?> updateAttractionInfo() {
		return attractionService.getAttractionInfo();
	}
	
	@GetMapping("/cities")
	public ResponseEntity<?> getSidoData() {
		return attractionQueryService.selectAllCity();
	}
	
	@GetMapping("/cities/{cityCode}/towns")
	public ResponseEntity<?> getGunguData(@PathVariable("cityCode") String cityCode) {
		return attractionQueryService.selectGugunBySidoId(cityCode);
	}
}
