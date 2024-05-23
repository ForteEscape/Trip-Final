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
import com.trip.service.TripCommandService;
import com.trip.service.TripQueryService;
import com.trip.vo.TripRequest.SearchFilter;
import com.trip.vo.TripRequest.TripPlan;
import com.trip.vo.TripRequest.TripReply;
import com.trip.vo.TripResponse.AttractionInfo;
import com.trip.vo.TripResponse.TripPlanDetail;
import com.trip.vo.TripResponse.TripPlanReply;
import com.trip.vo.TripResponse.TripPlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/trips")
@Tag(name = "여행 계획 API", description = "여행 계획 관련 api")
public class TripController {

	private final TripCommandService tripService;
	private final TripQueryService tripQueryService;
	private final Response response;

	@Operation(summary = "여행지 조회 api", description = "여행지 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 리스트 데이터 불러오기 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation =  AttractionInfo.class)))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/search")
	public ResponseEntity<?> searchAttraction(@RequestBody SearchFilter searchFilter) {
		return tripQueryService.searchAttraction(searchFilter);
	}

	@Operation(summary = "여행 계획 추가", description = "여행 계획 추가 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 추가 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public ResponseEntity<?> addNewTripPlan(@Validated @RequestBody TripPlan tripPlan, Errors errors,
			Principal principal) {
		if (errors.hasErrors()) {
			return response.invalidFields(Helper.refineErrors(errors));
		}

		return tripService.addTripPlan(tripPlan, principal.getName());
	}

	@Operation(summary = "여행 계획 리스트 조회", description = "여행 계획 리스트 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 리스트 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation = TripPlanResponse.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<?> getTripPlan(Principal principal) {
		return tripQueryService.getTripPlan(principal.getName());
	}

	@Operation(summary = "여행 계획 선택", description = "여행 계획 선택 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 선택 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/select")
	public ResponseEntity<?> selectTripPlan(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true) 
			String planId, Principal principal) {
		return tripService.selectTripPlan(planId, principal.getName());
	}

	@Operation(summary = "여행 계획 선택 취소", description = "여행 계획 선택 취소 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 선택 취소 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/unselect")
	public ResponseEntity<?> unselectTripPlan(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId, Principal principal) {
		return tripService.unselectTripPlan(planId, principal.getName());
	}

	@Operation(summary = "여행 계획 상세 페이지 조회", description = "여행 계획 상세 페이지 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 상세 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  TripPlanDetail.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}")
	public ResponseEntity<?> getTripPlanDetail(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId, Principal principal) {
		return tripQueryService.getTripDetail(planId, principal.getName());
	}

	@Operation(summary = "여행 계획 삭제", description = "여행 계획 삭제 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/{planId}")
	public ResponseEntity<?> deleteTripPlan(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId, Principal principal) {
		return tripService.deleteTripPlan(planId, principal.getName());
	}

	@Operation(summary = "여행 계획 댓글 추가", description = "여행 계획 댓글 추가 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 댓글 추가 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/{planId}/replies")
	public ResponseEntity<?> createNewReply(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId, 
			@Valid @RequestBody TripReply reply,
			Principal principal) {

		return tripService.createNewReply(planId, reply, principal.getName());
	}

	@Operation(summary = "여행 계획 댓글 리스트 조회", description = "여행 계획 댓글 리스트 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 댓글 리스트 조회 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = TripPlanReply.class)))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{planId}/replies")
	public ResponseEntity<?> getAllReplies(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId, Principal principal) {
		return tripQueryService.getAllReplies(planId, principal.getName());
	}

	@Operation(summary = "여행 계획 댓글 삭제", description = "여행 계획 댓글 삭제 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "여행 계획 댓글 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/{planId}/replies/{replyId}")
	public ResponseEntity<?> deleteRepliy(
			@PathVariable("planId") 
			@Parameter(name = "planId", description = "계획 id", example = "1", required = true)
			String planId,
			@PathVariable("replyId") 
			@Parameter(name = "replyId", description = "댓글 id", example = "1", required = true)
			String replyId, Principal principal) {

		return tripService.deleteReply(planId, replyId, principal.getName());
	}
}
