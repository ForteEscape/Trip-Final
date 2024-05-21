package com.trip.service;

import org.springframework.http.ResponseEntity;

import com.trip.vo.TripRequest.SearchFilter;

public interface TripQueryService {

	ResponseEntity<?> searchAttraction(SearchFilter searchFilter);

	ResponseEntity<?> getTripPlan(String userEmail);

	ResponseEntity<?> getTripDetail(String planId, String userEmail);
	
	ResponseEntity<?> getAllReplies(String planId, String userEmail);
}
