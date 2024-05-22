package com.notice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;

public interface NoticeService {

	ResponseEntity<?> getAllNotice(int offset);

	ResponseEntity<?> getOneNotice(String noticeId);

	ResponseEntity<?> updateNotice(String noticeId, List<MultipartFile> images, ModifiedNotice modifiedNotice, String userEmail);

	ResponseEntity<?> deleteNotice(String noticeId, String userEmail);

	ResponseEntity<?> createNotice(List<MultipartFile> files, NoticeData noticeData, String userEmail);
}
