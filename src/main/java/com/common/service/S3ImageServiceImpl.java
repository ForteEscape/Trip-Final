package com.common.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.common.exception.CustomException;
import com.common.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3ImageServiceImpl implements S3ImageService {
	
	private final AmazonS3 amazonS3;
	
	@Value("${cloud.aws.s3.bucket-name}")
	private String bucketName;
	
	@Value("${cloud.aws.s3.profile-dir}")
	private String profileDir;
	
	@Value("${cloud.aws.s3.hotplace-dir}")
	private String hotplaceDir;
	
	@Value("${cloud.aws.s3.notice-dir}")
	private String noticeDir;
	
	@Value("${cloud.aws.s3.trip-plan-dir}")
	private String tripPlanDir;
	
	private static final String UTF8 = "UTF-8";

	@Override
	public String upload(MultipartFile image) {
		if(image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
			return "";
		}
		
		return this.uploadImage(image);
	}

	private String uploadImage(MultipartFile image) {
		validateImageFileExtension(image.getOriginalFilename());
		
		try {
			return this.uploadToS3Bucket(image);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD);
		}
	}
	
	

	private String uploadToS3Bucket(MultipartFile image) throws IOException {
		String originalFileName = image.getOriginalFilename();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String s3FileName = profileDir + "/" + UUID.randomUUID()
				.toString()
				.substring(0, 10) + originalFileName;
		
		InputStream inputStream = image.getInputStream();
		byte[] byteArray = IOUtils.toByteArray(inputStream);
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType("image/" + extension);
		metadata.setContentLength(byteArray.length);
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		
		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
					.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			byteArrayInputStream.close();
			inputStream.close();
		}
		
		return amazonS3.getUrl(bucketName, s3FileName).toString();
	}

	private void validateImageFileExtension(String originalFilename) {
		// TODO Auto-generated method stub
		int lastDotIdx = originalFilename.lastIndexOf(".");
		
		if(lastDotIdx == -1) {
			throw new CustomException(ErrorCode.NO_FILE_EXTENSION);
		}
		
		String extension = originalFilename.substring(lastDotIdx + 1).toLowerCase();
		Set<String> allowedExtensionList = new HashSet<>(List.of("jpg", "jpeg", "png"));
		
		if(!allowedExtensionList.contains(extension)) {
			throw new CustomException(ErrorCode.INVALID_FILE_EXTENSION);
		}
	}

	@Override
	public void deleteImage(String imageAddress) {
		String key = getKeyFromImageAddress(imageAddress);
		
		try {
			amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
		}
	}

	private String getKeyFromImageAddress(String imageAddress) {
		try {
			URL url = new URL(imageAddress);
			String decodingKey = URLDecoder.decode(url.getPath(), UTF8);
			
			return decodingKey.substring(1);
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			throw new CustomException(ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE);
		}
	}
	
	
}
