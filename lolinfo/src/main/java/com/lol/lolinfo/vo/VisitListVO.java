package com.lol.lolinfo.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitListVO {

	private Date visitDate; 
	private int visitCount; // 하루에 접속한 실인원
	private int visitLogin; // 실인원 중 로그인 인원
	
}
