package com.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.notice.entity.NoticeEntity;

@Mapper
public interface NoticeMapper {

	void insertNotice(NoticeEntity entity);

	NoticeEntity selectOne(String noticeId);

	void updateNotice(NoticeEntity entity);

	void deleteNotice(int id);

	List<NoticeEntity> selectAll(int offset);

	int countTotalNotice();

	List<String> selectImageByNoticeId(String noticeId);
	
	

}
