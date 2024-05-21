package com.trip.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.dto.Response;
import com.common.util.Helper;
import com.trip.service.TripService;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripRequest.TripPlan;

import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripController {
	
	private final TripService tripService;
	private final Response response;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/search")
	public ResponseEntity<?> searchAttraction(@RequestBody SearchFilter searchFilter) {
		return tripService.searchAttraction(searchFilter);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public ResponseEntity<?> addNewTripPlan(@Validated @RequestBody TripPlan tripPlan, Errors errors, Principal principal) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		return tripService.addTripPlan(tripPlan, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getTripPlan(Principal principal) {
		return tripService.getTripPlan(principal.getName());
	}
	
	// 선택
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/select")
	public ResponseEntity<?> selectTripPlan(@PathVariable("planId") String planId, Principal principal) {
		return tripService.selectTripPlan(planId, principal.getName());
	}
	
	// 선택 취소
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/unselect")
	public ResponseEntity<?> unselectTripPlan(@PathVariable("planId") String planId, Principal principal) {
		return tripService.unselectTripPlan(planId, principal.getName());
	}
	
	@GetMapping("/{planId}")
	public ResponseEntity<?> getTripPlanDetail(@PathVariable("planId") String planId) {
		return tripService.getTripDetail(planId);
	}
} 
 