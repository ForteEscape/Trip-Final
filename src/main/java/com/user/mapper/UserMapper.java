package com.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.user.vo.User;

@Mapper
public interface UserMapper {

	User selectByEmail(String email);

	void insert(User newUser);

}
