package com.common.service;

import org.springframework.web.multipart.MultipartFile;

import com.common.util.Directory;

public interface S3ImageService {
	
	String upload(MultipartFile image, Directory directory);
	
	void deleteImage(String imageAddress);
}
