package com.notice.controller;

import java.security.Principal;

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
import org.springframework.web.bind.annotation.RestController;

import com.notice.service.NoticeService;
import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {

	private final NoticeService noticeService;
	private static final int CONTENT_PER_PAGE = 10;

	@GetMapping
	public ResponseEntity<?> getAllNotice(@RequestParam("page") int page) {
		int offset = (page - 1) * CONTENT_PER_PAGE;
		
		return noticeService.getAllNotice(offset);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<?> createNotice(@Valid @RequestBody NoticeData noticeData, Principal principal) {
		return noticeService.createNotice(noticeData, principal.getName());
	}

	@GetMapping("/{noticeId}")
	public ResponseEntity<?> getOneNotice(@PathVariable("noticeId") String noticeId) {
		return noticeService.getOneNotice(noticeId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{noticeId}")
	public ResponseEntity<?> updateNotice(@PathVariable("noticeId") String noticeId,
			@Valid @RequestBody ModifiedNotice modifiedNotice, Principal principal) {
		return noticeService.updateNotice(noticeId, modifiedNotice, principal.getName());
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{noticeId}")
	public ResponseEntity<?> deleteNotice(@PathVariable("noticeId") String noticeId, Principal principal) {
		return noticeService.deleteNotice(noticeId, principal.getName());
	}
}
