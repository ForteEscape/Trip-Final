package com.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.user.entity.User;

@Mapper
public interface UserMapper {

	User selectByEmail(String email);

	void insert(User newUser);

}
