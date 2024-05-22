package com.notice.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.notice.entity.NoticeEntity;

@Mapper
public interface NoticeCommandMapper {

	void insertNotice(NoticeEntity entity);

	void updateNotice(NoticeEntity entity);

	void deleteNotice(int id);

	void insertImages(Map<String, Object> paramMap);

	void deleteImageByNoticeId(String noticeId);

	void increaseViewCount(int id);

}
