package com.notice.vo;

import java.util.List;

import com.notice.entity.NoticeEntity;

import lombok.Builder;

public record NoticeResponse() {
	
	public static record PageInfo(
			int offset,
			List<Notice> items,
			int totalCount) {
		
	}
	
	@Builder
	public static record Notice(
			int id,
			int userId,
			String author,
			String title,
			String content,
			String writeDate,
			int viewCount) {
		
		public static Notice from(NoticeEntity entity) {
			return Notice.builder()
					.id(entity.getId())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.title(entity.getTitle())
					.content(entity.getContent())
					.writeDate(entity.getWriteDate())
					.viewCount(entity.getViewCount())
					.build();
		}
	}
	
	@Builder
	public static record NoticeDetail(
			int id,
			int userId,
			String author,
			String title,
			String content,
			String writeDate,
			int viewCount,
			List<String> images) {
		
		public static NoticeDetail from(NoticeEntity entity, List<String> images) {
			return NoticeDetail.builder()
					.id(entity.getId())
					.userId(entity.getUserId())
					.author(entity.getAuthor())
					.title(entity.getTitle())
					.content(entity.getContent())
					.writeDate(entity.getWriteDate())
					.viewCount(entity.getViewCount())
					.images(images)
					.build();
		}
	}
}
