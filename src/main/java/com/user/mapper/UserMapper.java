package com.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.user.entity.User;
import com.user.entity.UserInfo;

@Mapper
public interface UserMapper {

	User selectByEmail(String email);

	void insert(User newUser);
	
	UserInfo selectUserInfoByEmail(String email);

	void updateUser(User user);

	void updatePassword(User user);

}
