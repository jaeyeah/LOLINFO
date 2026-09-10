package com.lol.lolinfo.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkBalanceVO {
	private Integer opponentNo;
	private String opponentName;
	private String opponentSoopId;
	private String position;
	private Date lastMatchDate;
	private Integer winCount;
	private Integer loseCount;
	
	public int getMatchCount() {
	    return nvl(winCount) + nvl(loseCount);
	}
	
	public double getWinRate() {
		int total = getMatchCount();
		if(total==0) return 0.0;
		return Math.round(((double) nvl(winCount)/total*100)*10)/10.0;
	}
	
	private int nvl(Integer value) {
		return value == null ? 0 : value;
	}
	
}
