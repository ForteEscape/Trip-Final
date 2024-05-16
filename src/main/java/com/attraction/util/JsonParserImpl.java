package com.attraction.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.attraction.entity.AttractionDescription;
import com.attraction.entity.AttractionInfo;
import com.attraction.entity.Gugun;
import com.attraction.entity.Sido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonParserImpl implements JsonParser {

	private final ObjectMapper objectMapper;
	private final CodeMapper codeMapper;
	private Map<Integer, Set<Integer>> codeMap = null;

	@Override
	public List<Sido> parseSidoFromJson(String jsonString) throws JsonMappingException, JsonProcessingException {
		JsonNode tempNode = returnItemNode(jsonString);

		List<Sido> sidoList = new ArrayList<>();
		int idx = 0;

		while (tempNode.get(idx) != null) {
			int sidoCode = Integer.parseInt(tempNode.get(idx).get("code").textValue());
			String sidoName = tempNode.get(idx).get("name").textValue();

			sidoList.add(new Sido(sidoCode, sidoName));
			idx++;
		}

		return sidoList;
	}

	@Override
	public List<Gugun> parseGugunFromJson(int sidoCode, String jsonString) throws JsonMappingException, JsonProcessingException {
		JsonNode tempNode = returnItemNode(jsonString);

		List<Gugun> gugunList = new ArrayList<>();

		int idx = 0;
		while (tempNode.get(idx) != null) {
			int gugunCode = Integer.parseInt(tempNode.get(idx).get("code").textValue());
			String gugunName = tempNode.get(idx).get("name").textValue();

			gugunList.add(new Gugun(gugunCode, gugunName, sidoCode));
			idx++;
		}

		return gugunList;

	}

	@Override
	public List<AttractionInfo> parseAttractionInfoFromJson(String jsonString) throws JsonMappingException, JsonProcessingException {
		JsonNode tempNode = returnItemNode(jsonString);
		
		List<AttractionInfo> attractionList = new ArrayList<>();
		int idx = 0;
		
		if(codeMap == null) {
			codeMap = codeMapper.getGugunMap();
		}
		
		while(tempNode.get(idx) != null) {
			try {
				JsonNode node = tempNode.get(idx);
				
				AttractionInfo info = AttractionInfo.builder()
						.contentId(Integer.parseInt(node.get("contentid").textValue()))
						.sidoCode(Integer.parseInt(node.get("areacode").textValue()))
						.gugunCode(Integer.parseInt(node.get("sigungucode").textValue()))
						.contentTypeId(Integer.parseInt(node.get("contenttypeid").textValue()))
						.title(node.get("title").textValue())
						.addr1(node.get("addr1").textValue())
						.addr2(node.get("addr2").textValue())
						.zipCode(node.get("zipcode").textValue())
						.tel(node.get("tel").textValue())
						.firstImage(node.get("firstimage").textValue())
						.latitude(Double.parseDouble(node.get("mapx").textValue()))
						.longitude(Double.parseDouble(node.get("mapy").textValue()))
						.build();
				
				if(!codeMap.containsKey(info.getSidoCode())) {
					continue;
				}
				
				if(!codeMap.get(info.getSidoCode()).contains(info.getGugunCode())) {
					continue;
				}
				
				attractionList.add(info);
			} catch (Exception e) {
				continue;
			} finally {
				idx++;
			}
		}
		
		return attractionList;
	}

	@Override
	public AttractionDescription parseAttractionDescriptionFromJson(String jsonString) throws JsonMappingException, JsonProcessingException {
		JsonNode tempNode = returnItemNode(jsonString);
		
		int contentId = Integer.parseInt(tempNode.get(0).get("contentid").textValue());
		String homepage = tempNode.get(0).get("homepage").textValue();
		String overView = tempNode.get(0).get("overview").textValue();
		String telname = tempNode.get(0).get("telname").textValue();
		
		AttractionDescription description = AttractionDescription.builder()
				.contentId(contentId)
				.homepage(homepage)
				.overview(overView)
				.telname(telname)
				.build();

		return description;
	}

	private JsonNode returnItemNode(String jsonString) throws JsonMappingException, JsonProcessingException {
		JsonNode jsonNode = objectMapper.readTree(jsonString);

		return jsonNode.get("response").get("body").get("items").get("item");
	}

}
