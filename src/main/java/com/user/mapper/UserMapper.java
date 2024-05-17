package com.user.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.user.entity.User;
import com.user.entity.UserInfo;
import com.user.vo.UserRequest.Email;

@Mapper
public interface UserMapper {

	User selectByEmail(String email);

	void insert(User newUser);
	
	UserInfo selectUserInfoByEmail(String email);

	void updateUser(User user);

	void updatePassword(User user);

	User selectByUserNameAndPhone(Map<String, String> paramMap);

}
