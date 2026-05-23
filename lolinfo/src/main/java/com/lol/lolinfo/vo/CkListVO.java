package com.lol.lolinfo.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.CkParticipantDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkListVO {

	private Integer ckId;
	private Date ckDate;
	private String ckWinner;
	private String ckMemo;
	private Timestamp ckCreatedAt;
	private String ckCreatedBy;
	
	private Integer ckParticipantId;
	private Integer ckStreamer;
	private String ckSide;
	private String ckPosition;
	
	private String streamerName;
	
}
