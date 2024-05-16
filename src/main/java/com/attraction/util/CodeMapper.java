package com.attraction.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.attraction.entity.Gugun;
import com.attraction.mapper.AttractionQueryMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CodeMapper {
	
	private final AttractionQueryMapper attractionQueryMapper;
	
	public Map<Integer, Set<Integer>> getGugunMap() {
		List<Gugun> resultList = attractionQueryMapper.selectAllGugun();
		Map<Integer, Set<Integer>> resultMap = new HashMap<>();
		
		for(Gugun element : resultList) {
			if(!resultMap.containsKey(element.getSidoCode())) {
				resultMap.put(element.getSidoCode(), new HashSet<>());
			}
			
			resultMap.get(element.getSidoCode()).add(element.getGugunCode());
		}
		
		return resultMap;
	}
}
