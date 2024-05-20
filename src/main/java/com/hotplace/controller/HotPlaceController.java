package com.hotplace.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotplace.service.HotplaceService;
import com.hotplace.vo.HotplaceRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/hotplaces")
public class HotPlaceController {

	private final HotplaceService hotPlaceService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping
	public ResponseEntity<?> addNewHotPlace(@RequestPart("images") List<MultipartFile> images,
			@RequestPart("data") @Valid HotplaceRequest.HotPlace hotplace, Principal principal) {

		return hotPlaceService.addNewHotPlace(images, hotplace, principal.getName());
	}

	@GetMapping
	public ResponseEntity<?> getHotPlaces(@RequestParam("offset") int offset) {
		return hotPlaceService.getHotPlaceInfo(offset);
	}
	
	@GetMapping("/{hotplaceId}")
	public ResponseEntity<?> getHotPlaceDetail(@PathVariable("hotplaceId") String hotPlaceId) {
		return hotPlaceService.getHotPlaceDeatil(hotPlaceId);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/{hotplaceId}/recommend")
	public ResponseEntity<?> recommendHotPlace(@PathVariable("hotplaceId") String hotPlaceId,
			Principal principal) {
		
		return hotPlaceService.recommendHotPlace(hotPlaceId, principal.getName());
	}
	
	@GetMapping("/top-recommends")
	public ResponseEntity<?> getRecommendTopFive() {
		return hotPlaceService.getRecommendTop();
	}
}
