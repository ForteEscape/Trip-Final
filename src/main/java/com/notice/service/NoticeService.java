package com.notice.service;

import org.springframework.http.ResponseEntity;

import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;

public interface NoticeService {

	ResponseEntity<?> getAllNotice(int offset);

	ResponseEntity<?> getOneNotice(String noticeId);

	ResponseEntity<?> updateNotice(String noticeId, ModifiedNotice modifiedNotice, String userEmail);

	ResponseEntity<?> deleteNotice(String noticeId, String userEmail);

	ResponseEntity<?> createNotice(NoticeData noticeData, String userEmail);

	ResponseEntity<?> getLatestNotice();
}
