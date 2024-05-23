package com.hotplace.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hotplace.entity.HotPlaceRecommendEntity;
import com.hotplace.entity.HotPlaceReplyEntity;

@Mapper
public interface HotPlaceCommandMapper {

	void insertHotPlaceImages(Map<String, Object> paramMap);
	
	void insertRecommendRecord(HotPlaceRecommendEntity entity);

	void updateRecommendRecord(Map<String, Object> paramMap);
	
	int insertHotPlace(Map<String, Object> paramMap);

	void insertReply(HotPlaceReplyEntity replyEntity);

	void deleteReply(String replyId);
	
}
