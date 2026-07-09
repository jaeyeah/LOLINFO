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
public class CkRankingVO {

	private int streamerNo;
	private String streamerName;
	private String streamerSoopId;
	private int playCount;
	private int winCount;
	private int loseCount;
	private double winRate;
	
}
