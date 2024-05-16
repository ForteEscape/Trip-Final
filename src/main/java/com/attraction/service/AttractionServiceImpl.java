package com.attraction.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attraction.entity.AttractionDescription;
import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;
import com.attraction.mapper.AttractionCommandMapper;
import com.attraction.util.DataCrawler;
import com.attraction.util.JsonParser;
import com.common.dto.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService {

	private final JsonParser jsonParser;
	private final AttractionCommandMapper attractionMapper;
	private final DataCrawler dataCrawler;
	private final Response response;

	@Override
	@Transactional
	public ResponseEntity<?> getAreaInfo() {
		try {
			String jsonString = dataCrawler.getSido();

			List<Sido> resultList = jsonParser.parseSidoFromJson(jsonString);
			attractionMapper.insertSidoData(resultList);

			for (Sido element : resultList) {
				jsonString = dataCrawler.getGuGun(element);

				List<Gugun> gugunList = jsonParser.parseGugunFromJson(element.getSidoCode(), jsonString);
				attractionMapper.insertGunGuData(gugunList);
			}
		} catch (SQLException e) {
			return response.fail("오류 발생 - SQLException", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			return response.fail("오류 발생 - IOException", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response.success("지역 데이터 갱신 성공");
	}

	@Override
	@Transactional
	public ResponseEntity<?> getAttractionInfo() {
		try {
			String jsonString = dataCrawler.getAttractionInfo("1");
			List<AttractionInfo> resultList = jsonParser.parseAttractionInfoFromJson(jsonString);
			
			for(AttractionInfo info : resultList) {
				attractionMapper.testInsert(info);
			}
			
		} catch (IOException e) {
			return response.fail("오류 발생 - IOException", HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (SQLException e) {
			return response.fail("오류 발생 - SQLException", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response.success("관광지 데이터 갱신 성공");
	}
	
	@Transactional
	private void insertData(List<AttractionInfo> list) throws SQLException {
		log.info("start");
		attractionMapper.insertAttractionInfo(list);
		log.info("end");
	}

	public void getAttractionDescription(List<AttractionInfo> resultList) throws IOException, SQLException {
		List<AttractionDescription> descriptionList = new ArrayList<>();

		for (AttractionInfo info : resultList) {
			String jsonString = dataCrawler.getDetail(info.getContentId());

			AttractionDescription description = jsonParser.parseAttractionDescriptionFromJson(jsonString);
			descriptionList.add(description);
		}

		attractionMapper.insertAttractionDescription(descriptionList);
	}

}
