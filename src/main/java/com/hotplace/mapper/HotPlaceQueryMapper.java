package com.hotplace.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hotplace.entity.HotPlaceInfoEntity;
import com.hotplace.entity.HotPlaceRecommendEntity;
import com.hotplace.entity.HotPlaceReplyEntity;

@Mapper
public interface HotPlaceQueryMapper {

	int countAllHotPlace();
	
	HotPlaceInfoEntity selectHotplaceInfoByHotPlaceId(String hotPlaceId);

	HotPlaceRecommendEntity getRecommendRecord(Map<String, Object> paramMap);

	List<HotPlaceInfoEntity> selectAllHotPlaceInfo();

	List<HotPlaceRecommendEntity> selectRecommendByHid(int id);
	
	List<HotPlaceInfoEntity> selectAllHotPlace(int offset);
	
	List<String> selectHotplaceImageByHotPlaceId(String hotPlaceId);
	
	List<HotPlaceReplyEntity> selectAllReply(String hotplaceId);
	
	HotPlaceReplyEntity selectOne(String replyId);
}
