package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BookmarkStreamerVO {
	
	private int streamerNo;
	private String streamerName;
	private String streamerSoopId;

	// 공식 기록
	private int officialRanking1; //우승
	private int officialRanking2; //준우승
	private int officialRanking3; //4강
	
	//CK 기록
	private int ckPlayCount; // ck전적
	private int ckWinCount; // ck승리
	private int ckLoseCount; // ck패배
    public double getCkWinRate() {
    	if (ckPlayCount == 0) return 0;
    	return Math.round((ckWinCount * 1000.0 / ckPlayCount)) / 10.0;
    }

}
