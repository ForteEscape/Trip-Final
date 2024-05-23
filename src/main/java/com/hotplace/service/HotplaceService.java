package com.hotplace.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.hotplace.vo.HotplaceRequest;
import com.hotplace.vo.HotplaceRequest.Reply;

import jakarta.validation.Valid;

public interface HotplaceService {

	ResponseEntity<?> addNewHotPlace(List<MultipartFile> images, HotplaceRequest.HotPlace hotPlace, String userEmail);

	ResponseEntity<?> getHotPlaceInfo(int offset);

	ResponseEntity<?> getHotPlaceDeatil(String hotPlaceId);

	ResponseEntity<?> recommendHotPlace(String hotPlaceId, String userEmail);

	ResponseEntity<?> getRecommendTop();

	ResponseEntity<?> addNewReply(String hotplaceId, Reply reply, String userEmail);

	ResponseEntity<?> getAllReply(String hotplaceId);

	ResponseEntity<?> deleteReply(String replyId, String userEmail);
	
}
