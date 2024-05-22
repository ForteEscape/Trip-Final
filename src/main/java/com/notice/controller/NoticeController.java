package com.notice.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotplace.vo.HotplaceResponse.HotPlaceDetail;
import com.notice.service.NoticeService;
import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;
import com.notice.vo.NoticeResponse.Notice;
import com.notice.vo.NoticeResponse.NoticeDetail;
import com.notice.vo.NoticeResponse.PageInfo;
import com.trip.vo.TripResponse.AttractionInfo;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/notices")
@Tag(name = "공지사항 API", description = "공지사항 관련 api")
public class NoticeController {

	private final NoticeService noticeService;
	private static final int CONTENT_PER_PAGE = 10;

	@Operation(summary = "공지사항 리스트 api", description = "공지사항 리스트 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "공지사항 리스트 불러오기 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation =  PageInfo.class)))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping
	public ResponseEntity<?> getAllNotice(@RequestParam("page") int page) {
		int offset = (page - 1) * CONTENT_PER_PAGE;

		return noticeService.getAllNotice(offset);
	}

	@Operation(summary = "공지사항 추가", description = "공지사항 추가 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "공지사항 추가 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<?> createNotice(@Valid @RequestBody NoticeData noticeData, Principal principal) {
		return noticeService.createNotice(noticeData, principal.getName());
	}

	@Operation(summary = "공지사항 상세 조회", description = "공지사항 상세 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "공지사항 상세 조회 데이터 불러오기 성공", content = {
					@Content(schema = @Schema(implementation =  NoticeDetail.class))
			}),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/{noticeId}")
	public ResponseEntity<?> getOneNotice(@PathVariable("noticeId") String noticeId) {
		return noticeService.getOneNotice(noticeId);
	}

	@Operation(summary = "공지사항 수정", description = "공지사항 수정 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "공지사항 수정 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{noticeId}")
	public ResponseEntity<?> updateNotice(@PathVariable("noticeId") String noticeId,
			@Valid @RequestBody ModifiedNotice modifiedNotice, Principal principal) {
		
		return noticeService.updateNotice(noticeId, modifiedNotice, principal.getName());
	}

	@Operation(summary = "공지사항 삭제", description = "공지사항 삭제 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "공지사항 삭제 성공"),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{noticeId}")
	public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") String noticeId, Principal principal) {
		return noticeService.deleteNotice(noticeId, principal.getName());
	}
	
	@Operation(summary = "최신 공지사항 조회", description = "최신 공지사항 5개 조회 api")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "최신 공지사항 조회 성공", content = {
					@Content(array = @ArraySchema(schema = @Schema(description = "공지 리스트", implementation = Notice.class)))
			}),
			@ApiResponse(responseCode = "400", description = "입력 값에 오류가 있습니다."),
			@ApiResponse(responseCode = "500", description = "서버 에러가 발생했습니다.")
	})
	@GetMapping("/latest")
	public ResponseEntity<?> getLatestNotice() {
		return noticeService.getLatestNotice();
	}
}
