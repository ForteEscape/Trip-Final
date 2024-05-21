package com.notice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notice.mapper.NoticeMapper;
import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	private final NoticeMapper noticeMapper;

	@Override
	public ResponseEntity<?> getAllNotice(int offset) {
		return null;
	}
	
	@Override
	public ResponseEntity<?> getOneNotice(String noticeId) {
		return null;
	}

	@Override
	public ResponseEntity<?> createNotice(NoticeData noticeData, String userEmail) {
		return null;
	}

	@Override
	public ResponseEntity<?> updateNotice(String noticeId, ModifiedNotice modifiedNotice, String userEmail) {
		return null;
	}

	@Override
	public ResponseEntity<?> deleteNotice(String noticeId, String userEmail) {
		return null;
	}
}
