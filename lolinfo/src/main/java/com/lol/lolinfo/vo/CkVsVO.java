package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//맞라인 상대전적을 계산하기 위한 VO
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkVsVO {
	
	public Integer vsStreamerNo;
	public String vsStreamerName;
	public String ckPosition;

	public Integer winCount;
	public Integer loseCount;
	
	public int getTotalCount() {
		return (winCount == null ? 0 : winCount)
		     + (loseCount == null ? 0 : loseCount);
	}
	public double getWinRate() {
		if(getTotalCount()==0) return 0;
		return Math.round(((double)winCount/getTotalCount())*1000)/10.0;
	}
	
	
}
