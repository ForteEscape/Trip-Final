package com.trip.service;

import org.springframework.http.ResponseEntity;

import com.trip.vo.TripRequest.TripPlan;
import com.trip.vo.TripRequest.TripReply;

public interface TripCommandService {

	ResponseEntity<?> addTripPlan(TripPlan tripPlan, String userEmail);

	ResponseEntity<?> selectTripPlan(String planId, String userEmail);

	ResponseEntity<?> unselectTripPlan(String planId, String name);

	ResponseEntity<?> deleteTripPlan(String planId, String userEmail);

	ResponseEntity<?> createNewReply(String planId, TripReply reply, String userEmail);

	ResponseEntity<?> deleteReply(String planId, String replyId, String userEmail);
	
}
