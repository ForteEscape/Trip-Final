package com.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserInfo {
	
	private int id;
	private String sidoName;
	private String gugunName;
	private String name;
	private String email;
	private String phone;
	private String comment;
	private String userCode;
	private String profileImagePath;
	
}
