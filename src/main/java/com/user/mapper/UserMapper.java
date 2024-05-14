package com.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.user.vo.User;

@Mapper
public interface UserMapper {

	User selectByUserId(String id);

	User selectByEmailAndPassword(String email, String password);

}
