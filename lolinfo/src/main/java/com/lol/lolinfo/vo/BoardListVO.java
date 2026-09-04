package com.lol.lolinfo.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor 
public class BoardListVO {
	
	private Integer boardId;
	private String boardCategory;
	private String boardWriter;
	private String boardTitle;
	private Timestamp boardWtime;
	private String boardAdminCheck;
	
	private String memberNickname;

}
