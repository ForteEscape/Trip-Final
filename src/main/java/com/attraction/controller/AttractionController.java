package com.attraction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attraction.service.AttractionQueryService;
import com.attraction.service.AttractionService;
import com.attraction.vo.AttractionResponse.Gugun;
import com.attraction.vo.AttractionResponse.Sido;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/attraction")
@Tag(name = "관광지 API", description = "관광지 및 시/도 지역 관련 API")
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
	
	@Operation(summary = "시/도 데이터 api", description = "시/도 데이터 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "시/도 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  Sido.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/cities")
	public ResponseEntity<?> getSidoData() {
		return attractionQueryService.selectAllCity();
	}
	
	@Operation(summary = "군/구 데이터 api", description = "군/구 데이터 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "군/구 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  Gugun.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/cities/{cityCode}/towns")
	public ResponseEntity<?> getGunguData(
			@Parameter(name = "cityCode", description = "찾을 군/구의 소속 시/도 코드입니다.", example = "1", required = true) 
			@PathVariable("cityCode") String cityCode) {
		return attractionQueryService.selectGugunBySidoId(cityCode);
	}
}
