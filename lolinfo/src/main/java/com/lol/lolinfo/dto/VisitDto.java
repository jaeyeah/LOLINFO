package com.lol.lolinfo.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//등록용 DTO
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitDto {

//	private Date visitDate;
	private String visitorId; // ID만 넘어오면, 다른 요소는 Default값 적용
//	private String isLogin;
//	private Timestamp createdAt;
	
}
