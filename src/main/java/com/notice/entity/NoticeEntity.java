package com.notice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class NoticeEntity {
	
	private int id;
	private int userId;
	private String title;
	private String content;
	private String writeDate;
	private String author;
	private int viewCount;
	
	public void modifyTitle(String title) {
		this.title = title;
	}
	
	public void modifyContent(String content) {
		this.content = content;
	}
}
