package com.trip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.attraction.entity.AttractionInfo;
import com.trip.entity.TripPlanEntity;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripRequest.TripPlan;

@Mapper
public interface TripMapper {

	List<AttractionInfo> searchAttraction(SearchFilter searchFilter);

	int insertTripPlan(Map<String, Object> paramMap);

	void insertPlaces(Map<String, Object> paramMap);

	void insertTripMembers(Map<String, Object> paramMap);

}
