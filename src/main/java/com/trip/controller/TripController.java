package com.trip.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.dto.Response;
import com.common.util.Helper;
import com.trip.service.TripQueryService;
import com.trip.service.TripCommandService;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripRequest.TripPlan;
import com.trip.vo.TripRequest.TripReply;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/trips")
@Slf4j
public class TripController {
	
	private final TripCommandService tripService;
	private final TripQueryService tripQueryService;
	private final Response response;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/search")
	public ResponseEntity<?> searchAttraction(@RequestBody SearchFilter searchFilter) {
		return tripQueryService.searchAttraction(searchFilter);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public ResponseEntity<?> addNewTripPlan(@Validated @RequestBody TripPlan tripPlan, Errors errors, Principal principal) {
		if(errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}
		
		log.info("======================== start ====================");
		return tripService.addTripPlan(tripPlan, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getTripPlan(Principal principal) {
		return tripQueryService.getTripPlan(principal.getName());
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
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}")
	public ResponseEntity<?> getTripPlanDetail(@PathVariable("planId") String planId, Principal principal) {
		return tripQueryService.getTripDetail(planId, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/{planId}")
	public ResponseEntity<?> deleteTripPlan(@PathVariable("planId") String planId, Principal principal) {
		return tripService.deleteTripPlan(planId, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/{planId}/replies")
	public ResponseEntity<?> createNewReply(@PathVariable("planId") String planId,
			@Valid @RequestBody TripReply reply, Principal principal) {
		
		return tripService.createNewReply(planId, reply, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/replies")
	public ResponseEntity<?> getAllReplies(@PathVariable("planId") String planId, Principal principal) {
		return tripQueryService.getAllReplies(planId, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/{planId}/replies/{replyId}")
	public ResponseEntity<?> deleteRepliy(@PathVariable("planId") String planId, 
			@PathVariable("replyId") String replyId, Principal principal) {
		
		return tripService.deleteReply(planId, replyId, principal.getName());
	}
} 
 