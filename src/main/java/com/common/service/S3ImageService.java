package com.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3ImageService {
	
	String upload(MultipartFile image);
	
	void deleteImage(String imageAddress);
}
