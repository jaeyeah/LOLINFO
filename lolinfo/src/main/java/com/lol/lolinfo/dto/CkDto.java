package com.lol.lolinfo.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkDto {
	private Integer ckId;
	private Date ckDate;
	private String ckWinner;
	private String ckMemo;
	private Timestamp ckCreatedAt;
	private String ckCreatedBy;
}
