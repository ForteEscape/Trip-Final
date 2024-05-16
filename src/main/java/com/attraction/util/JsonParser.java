package com.attraction.util;

import java.util.List;

import com.attraction.entity.AttractionDescription;
import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface JsonParser {
	
	List<Sido> parseSidoFromJson(String jsonString) throws JsonMappingException, JsonProcessingException;
	
	List<Gugun> parseGugunFromJson(int sidoCode, String jsonString) throws JsonMappingException, JsonProcessingException;
	
	List<AttractionInfo> parseAttractionInfoFromJson(String jsonString) throws JsonMappingException, JsonProcessingException;
	
	AttractionDescription parseAttractionDescriptionFromJson(String jsonString) throws JsonMappingException, JsonProcessingException;
}
