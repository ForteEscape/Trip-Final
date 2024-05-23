package com.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record UserResponse(){
	
	@Schema(description = "유저 정보")
	@Builder
	public static record UserInfo(
			@Schema(description = "유저 id")
			int id,
			
			@Schema(description = "유저 이름")
			String name,
			
			@Schema(description = "유저 이메일")
			String email,
			
			@Schema(description = "유저 전화번호")
			String phone,
			
			@Schema(description = "유저 코멘트")
			String comment,
			
			@Schema(description = "유저 코드")
			String userCode,
			
			@Schema(description = "유저 프로필 이미지")
			String profileImagePath,
			
			@Schema(description = "유저 거주 시/도 이름")
			String sidoName,
			
			@Schema(description = "유저 거주 구/군 이름")
			String gugunName
	) {
		public static UserInfo from(com.user.entity.UserInfo userInfo) {
			return UserResponse.UserInfo.builder()
					.id(userInfo.getId())
					.name(userInfo.getName())
					.email(userInfo.getEmail())
					.phone(userInfo.getPhone())
					.comment(userInfo.getComment())
					.userCode(userInfo.getUserCode())
					.profileImagePath(userInfo.getProfileImagePath())
					.sidoName(userInfo.getSidoName())
					.gugunName(userInfo.getGugunName())
					.build();
		}
	}
}
