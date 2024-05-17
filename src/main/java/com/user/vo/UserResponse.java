package com.user.vo;

import lombok.Builder;

public record UserResponse(){
	
	@Builder
	public static record UserInfo(
			String name,
			String email,
			String phone,
			String comment,
			String userCode,
			String profileImagePath,
			String sidoName,
			String gugunName
	) {
		public static UserInfo from(com.user.entity.UserInfo userInfo) {
			return UserResponse.UserInfo.builder()
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
