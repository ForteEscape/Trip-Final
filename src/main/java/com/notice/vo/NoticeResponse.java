package com.notice.vo;

import java.util.List;

import com.notice.entity.NoticeEntity;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record NoticeResponse() {
	
	@Schema(description = "공지사항 페이징 DTO")
	public static record PageInfo(
			
			@Schema(description = "오프셋 값")
			int offset,
			
			@ArraySchema(schema = @Schema(description = "공지 리스트 데이터", implementation = Notice.class))
			List<Notice> items,
			
			@Schema(description = "전체 데이터 갯수")
			int totalCount) {
		
	}
	
	@Schema(description = "공지 DTO")
	@Builder
	public static record Notice(
			
			@Schema(description = "공지 id")
			int id,
			
			@Schema(description = "공지 작성자 id")
			int userId,
			
			@Schema(description = "공지 작성자 이름")
			String author,
			
			@Schema(description = "공지 이름")
			String title,
			
			@Schema(description = "공지 내용")
			String content,
			
			@Schema(description = "작성 일자")
			String writeDate,
			
			@Schema(description = "조회수")
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
	
	@Schema(description = "공지 상세 DTO")
	@Builder
	public static record NoticeDetail(
			@Schema(description = "공지 id")
			int id,
			
			@Schema(description = "공지 작성자 id")
			int userId,
			
			@Schema(description = "공지 작성자 이름")
			String author,
			
			@Schema(description = "공지 이름")
			String title,
			
			@Schema(description = "공지 내용")
			String content,
			
			@Schema(description = "작성 일자")
			String writeDate,
			
			@Schema(description = "조회수")
			int viewCount,
			
			@ArraySchema(schema = @Schema(description = "이미지 경로 데이터"))
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
