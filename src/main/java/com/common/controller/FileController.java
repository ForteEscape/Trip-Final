package com.common.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.service.S3ImageService;
import com.common.util.Directory;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin("*")
@RequestMapping("/util")
@RequiredArgsConstructor
@Tag(name = "파일 컨트롤러", description = "파일 데이터 업로딩을 위한 컨트롤러입니다.")
public class FileController {
	
	private final S3ImageService imageService;
	
	@PostMapping(path = "/upload/notice", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> uploadNoticeFiles(@RequestPart("images") List<MultipartFile> images) {
		List<String> imageList = new ArrayList<>();
		
		for(MultipartFile image : images) {
			imageList.add(imageService.upload(image, Directory.NOTICE));
		}
		
		return ResponseEntity.ok(imageList);
	}
	
	@PostMapping(path = "/upload/hotplace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> uploadHotPlaceFiles(@RequestPart("images") List<MultipartFile> images) {
		List<String> imageList = new ArrayList<>();
		
		for(MultipartFile image : images) {
			imageList.add(imageService.upload(image, Directory.HOTPLACE));
		}
		
		return ResponseEntity.ok(imageList);
	}
	
	
	@GetMapping(path = "/render/notice", produces = {
			MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE
	})
	public byte[] printImage(@RequestParam("path") String filePath) {
		File uploadFile = new File(filePath);
		
		try {
			byte[] imageBytes = Files.readAllBytes(uploadFile.toPath());
			
			return imageBytes;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
