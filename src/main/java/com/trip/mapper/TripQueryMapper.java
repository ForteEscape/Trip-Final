package com.trip.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.attraction.entity.AttractionInfo;
import com.trip.entity.TripPlanEntity;
import com.trip.entity.TripPlanReplyEntity;
import com.trip.vo.TripRequest.SearchFilter;

@Mapper
public interface TripQueryMapper {
	
	List<AttractionInfo> searchAttraction(SearchFilter searchFilter);
	
	List<TripPlanEntity> getSelectedTrip(int id);

	List<TripPlanEntity> getUnSelectedTrip(Map<String, Object> paramMap);

	int getSelectedTripByPlanId(Map<String, Object> paramMap);
	
	TripPlanEntity selectByPlanId(String planId);

	List<AttractionInfo> selectByPlanIdAndDay(Map<String, Object> paramMap);

	List<Integer> selectDay(String planId);

	List<String> getMemberByPlanId(String planId);
	
	List<TripPlanReplyEntity> selectAllReplies(String planId);

	TripPlanReplyEntity getReplyByIdAndPlanId(String replyId);
}
