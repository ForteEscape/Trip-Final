package com.hotplace.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotplace.service.HotplaceService;
import com.hotplace.vo.HotplaceRequest;
import com.hotplace.vo.HotplaceRequest.HotPlace;
import com.hotplace.vo.HotplaceRequest.Reply;
import com.hotplace.vo.HotplaceResponse.HotPlaceDetail;
import com.hotplace.vo.HotplaceResponse.HotPlaceInfo;
import com.hotplace.vo.HotplaceResponse.HotPlacePageInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/hotplaces")
@Tag(name = "핫 플레이스 API", description = "핫플레이스 관련 API")
public class HotPlaceController {

	private final HotplaceService hotPlaceService;
	private final int DATA_PER_PAGE = 12;

	@Operation(summary = "핫 플레이스 추가", description = "핫 플레이스 추가 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 추가 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameter(name = "hotplace", content = @Content(schema = @Schema(implementation = HotPlace.class)))
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<?> addNewHotPlace(@RequestPart(value = "images", required = false) List<MultipartFile> images,
			@RequestPart("data") @Valid HotplaceRequest.HotPlace hotplace, Principal principal) {

		return hotPlaceService.addNewHotPlace(images, hotplace, principal.getName());
	}

	@Operation(summary = "핫 플레이스 리스트 조회", description = "핫 플레이스 리스트 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 리스트 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  HotPlacePageInfo.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameter(name = "page", description = "조회할 페이지", example = "1", required = true)
	@GetMapping
	public ResponseEntity<?> getHotPlaces(@RequestParam(name="page", required = true) int page) {
		int offset = (page - 1) * DATA_PER_PAGE;
		
		return hotPlaceService.getHotPlaceInfo(offset);
	}
	
	@Operation(summary = "핫 플레이스 상세 페이지 조회", description = "핫 플레이스 상세 페이지 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 상세 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  HotPlaceDetail.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameter(name = "hotPlaceId", description = "핫플레이스 Id", example = "3", required = true)
	@GetMapping("/{hotplaceId}")
	public ResponseEntity<?> getHotPlaceDetail(
			@PathVariable("hotplaceId") String hotPlaceId) {
		return hotPlaceService.getHotPlaceDeatil(hotPlaceId);
	}

	@Operation(summary = "핫 플레이스 추천", description = "핫 플레이스 추천 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 추천 성공"),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@GetMapping("/{hotplaceId}/recommend")
	public ResponseEntity<?> recommendHotPlace(@PathVariable("hotplaceId") String hotPlaceId,
			Principal principal) {
		
		return hotPlaceService.recommendHotPlace(hotPlaceId, principal.getName());
	}
	
	@Operation(summary = "핫 플레이스 추천 상위 5개 조회", description = "핫 플레이스 추천 상위 5개 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "시/도 데이터 불러오기 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation =  HotPlaceInfo.class)))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/top-recommends")
	public ResponseEntity<?> getRecommendTopFive() {
		return hotPlaceService.getRecommendTop();
	}
	
	@Operation(summary = "핫 플레이스 댓글 추가", description = "핫 플레이스 댓글 추가 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 댓글 추가 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameters(value = {
			@Parameter(name = "reply", content = @Content(schema = @Schema(implementation = Reply.class))),
			@Parameter(name = "hotplaceId", description = "핫 플레이스 번호")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@PostMapping("/{hotplaceId}/replies")
	public ResponseEntity<?> addNewReply(@PathVariable("hotplaceId") String hotplaceId, 
			@RequestBody @Valid Reply reply, Principal principal) {

		return hotPlaceService.addNewReply(hotplaceId, reply, principal.getName());
	}
	
	@Operation(summary = "핫 플레이스 댓글 조회", description = "핫 플레이스 댓글 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 댓글 추가 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Reply.class)))
			}),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameter(name = "hotplaceId", description = "핫 플레이스 번호")
	@GetMapping("/{hotplaceId}/replies")
	public ResponseEntity<?> getAllReply(@PathVariable("hotplaceId") String hotplaceId) {
		return hotPlaceService.getAllReply(hotplaceId);
	}
	
	@Operation(summary = "핫 플레이스 댓글 삭제", description = "핫 플레이스 댓글 삭제 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "핫 플레이스 댓글 삭제 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = Reply.class)))
			}),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@Parameters(value = {
			@Parameter(name = "replyId", description = "댓글 번호"),
			@Parameter(name = "hotplaceId", description = "핫 플레이스 번호")
	})
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
	@DeleteMapping("/{hotplaceId}/replies/{replyId}")
	public ResponseEntity<?> deleteReply(@PathVariable("hotplaceId") String hotplaceId, 
			@PathVariable("replyId") String replyId, Principal principal) {
		return hotPlaceService.deleteReply(replyId, principal.getName());
	}
}
