package com.hotplace.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hotplace.entity.HotPlaceInfoEntity;
import com.hotplace.entity.HotPlaceRecommendEntity;
import com.hotplace.entity.HotPlaceReplyEntity;

@Mapper
public interface HotplaceMapper {

	void insertHotPlaceImages(Map<String, Object> paramMap);
	
	void insertRecommendRecord(HotPlaceRecommendEntity entity);

	void updateRecommendRecord(Map<String, Object> paramMap);

	int countAllHotPlace();
	
	int insertHotPlace(Map<String, Object> paramMap);

	HotPlaceInfoEntity selectHotplaceInfoByHotPlaceId(String hotPlaceId);

	HotPlaceRecommendEntity getRecommendRecord(Map<String, Object> paramMap);

	List<HotPlaceInfoEntity> selectAllHotPlaceInfo();

	List<HotPlaceRecommendEntity> selectRecommendByHid(int id);
	
	List<HotPlaceInfoEntity> selectAllHotPlace(int offset);
	
	List<String> selectHotplaceImageByHotPlaceId(String hotPlaceId);

	void inesrtReply(HotPlaceReplyEntity replyEntity);

}
