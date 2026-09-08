package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	//연승,연패기록 추가
	private String currentResult;
	private Integer currentStreak;
	
	
}
