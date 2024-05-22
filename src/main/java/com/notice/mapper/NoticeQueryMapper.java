package com.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.notice.entity.NoticeEntity;

@Mapper
public interface NoticeQueryMapper {
	
	int countTotalNotice();
	
	List<NoticeEntity> selectAll(int offset);

	List<String> selectImageByNoticeId(String noticeId);
	
	NoticeEntity selectOne(String noticeId);

	List<NoticeEntity> getLatestNotice();
	
}
