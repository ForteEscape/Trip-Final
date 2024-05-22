package com.notice.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.dto.Response;
import com.notice.entity.NoticeEntity;
import com.notice.mapper.NoticeCommandMapper;
import com.notice.mapper.NoticeQueryMapper;
import com.notice.vo.NoticeRequest.ModifiedNotice;
import com.notice.vo.NoticeRequest.NoticeData;
import com.notice.vo.NoticeResponse.Notice;
import com.notice.vo.NoticeResponse.NoticeDetail;
import com.notice.vo.NoticeResponse.PageInfo;
import com.user.entity.User;
import com.user.mapper.UserMapper;
import com.user.vo.RoleType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

	private final NoticeCommandMapper noticeCommandMapper;
	private final NoticeQueryMapper noticeQueryMapper;
	private final UserMapper userMapper;
	private final Response response;

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> getAllNotice(int offset) {
		List<Notice> result = noticeQueryMapper.selectAll(offset).stream().map(Notice::from).toList();

		int totalItem = noticeQueryMapper.countTotalNotice();
		PageInfo pageInfo = new PageInfo(offset, result, totalItem);

		return response.success(pageInfo, "공지 조회 성공", HttpStatus.OK);
	}

	@Transactional
	@Override
	public ResponseEntity<?> getOneNotice(String noticeId) {
		NoticeEntity entity = noticeQueryMapper.selectOne(noticeId);

		if (entity == null) {
			return response.fail("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}

		NoticeDetail result = NoticeDetail.from(entity);
		noticeCommandMapper.increaseViewCount(entity.getId());

		return response.success(result, "공지 상세 조회 성공", HttpStatus.OK);
	}

	@Transactional
	@Override
	public ResponseEntity<?> createNotice(NoticeData noticeData, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);

		if (user == null || !user.getRole().equals(RoleType.ADMIN.getRole())) {
			return response.fail("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
		}

		NoticeEntity entity = NoticeEntity.builder()
				.userId(user.getId())
				.title(noticeData.title())
				.content(noticeData.content())
				.viewCount(0)
				.build();

		noticeCommandMapper.insertNotice(entity);

		return response.success("공지 작성 성공");
	}

	@Transactional
	@Override
	public ResponseEntity<?> updateNotice(String noticeId, ModifiedNotice modifiedNotice,
			String userEmail) {
		User user = userMapper.selectByEmail(userEmail);

		if (user == null || !user.getRole().equals(RoleType.ADMIN.getRole())) {
			return response.fail("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
		}

		NoticeEntity entity = noticeQueryMapper.selectOne(noticeId);

		if (entity == null || entity.getUserId() != user.getId()) {
			return response.fail("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}
		
		entity.modifyTitle(modifiedNotice.title());
		entity.modifyContent(modifiedNotice.content());

		noticeCommandMapper.updateNotice(entity);

		return response.success("공지 수정 완료");
	}

	@Transactional
	@Override
	public ResponseEntity<?> deleteNotice(String noticeId, String userEmail) {
		User user = userMapper.selectByEmail(userEmail);

		if (user == null || !user.getRole().equals(RoleType.ADMIN.getRole())) {
			return response.fail("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
		}

		NoticeEntity entity = noticeQueryMapper.selectOne(noticeId);

		if (entity == null || entity.getUserId() != user.getId()) {
			return response.fail("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
		}

		noticeCommandMapper.deleteNotice(entity.getId());

		return response.success("공지 삭제 성공");
	}

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> getLatestNotice() {
		List<Notice> resultList = noticeQueryMapper.getLatestNotice().stream()
				.map(Notice::from)
				.toList();
		
		return response.success(resultList, "최신 공지 5개 조회 성공", HttpStatus.OK);
	}
}
