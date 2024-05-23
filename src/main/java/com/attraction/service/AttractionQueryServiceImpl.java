package com.attraction.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;
import com.attraction.mapper.AttractionQueryMapper;
import com.attraction.vo.AttractionResponse;
import com.attraction.vo.AttractionResponse.SimpleAttractionInfo;
import com.common.dto.Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionQueryServiceImpl implements AttractionQueryService {
	
	private final AttractionQueryMapper attractionMapper;
	private final Response response;
	private int attractionCount = -1;

	@Override
	public ResponseEntity<?> selectAllCity() {
		// TODO Auto-generated method stub
		List<Sido> resultList = attractionMapper.selectAllSido();
		
		List<AttractionResponse.Sido> data = resultList.stream()
			.map(e -> AttractionResponse.Sido.from(e))
			.collect(Collectors.toList());
		
		return response.success(data, "시 도 조회 성공", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> selectGugunBySidoId(String sidoCode) {
		// TODO Auto-generated method stub
		List<Gugun> resultList = attractionMapper.selectBySidoCode(sidoCode);
		
		List<AttractionResponse.Gugun> data = resultList.stream()
				.map(e -> AttractionResponse.Gugun.from(e))
				.collect(Collectors.toList());
		
		return response.success(data, "시군구 조회 성공", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> selectByContentId(String contentId) {
		AttractionInfo info = attractionMapper.selectByContentId(contentId);
		SimpleAttractionInfo result = SimpleAttractionInfo.from(info);
		
		return response.success(result, "관광지 데이터 조회 성공", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getAttractionCount() {
		if(attractionCount == -1) {
			attractionCount = attractionMapper.getAttractionCount();
		}
		
		return response.success(attractionCount, "관광지 갯수 조회 성공", HttpStatus.OK);
	}
	
	
}
