package com.lol.lolinfo.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class BoardDto {

	private Integer boardId;
	private String boardCategory;
	private Integer boardWriter;
	private String boardTitle;
	private String boardContent;
	private Timestamp boardWtime;
	private Timestamp boardEtime;
	private String boardAdminCheck;
	private Timestamp boardAdminTime;
}
