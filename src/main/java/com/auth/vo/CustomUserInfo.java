package com.auth.vo;

import com.user.vo.RoleType;
import com.user.vo.User;

public record CustomUserInfo(
		int userId,
		String email,
		String name,
		String password,
		RoleType role
) {
	
	public static CustomUserInfo of(User user) {
		return new CustomUserInfo(user.id(), 
				user.email(), 
				user.name(), 
				user.password(), 
				user.role()
			);
	}
}