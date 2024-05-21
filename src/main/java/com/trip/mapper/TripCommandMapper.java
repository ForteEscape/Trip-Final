package com.trip.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.trip.entity.TripPlanReplyEntity;

@Mapper
public interface TripCommandMapper {

	int insertTripPlan(Map<String, Object> paramMap);

	void insertPlaces(Map<String, Object> paramMap);

	void insertTripMembers(Map<String, Object> paramMap);

	void insertSelectedTrip(Map<String, Object> paramMap);

	void deleteSelectedTrip(Map<String, Object> paramMap);

	void deleteTripPlan(String planId);

	void insertReply(TripPlanReplyEntity replyEntity);

	void deleteReply(String replyId);

}
