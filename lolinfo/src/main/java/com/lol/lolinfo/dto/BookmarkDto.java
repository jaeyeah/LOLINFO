package com.lol.lolinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BookmarkDto {

	private String bookmarkMember;
	private String bookmarkType;
	private Integer bookmarkTarget;
}
