package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerWithCkVO {
	
	private int partnerNo;
	private String partnerName;
	private String partnerSoopId;
	
	private int playCount;
    private int winCount;
    private int loseCount;
    public double getWinRate() {
    	if(playCount==0) return 0;
    	return Math.round((winCount * 1000.0/playCount)/ 10.0);
    }
}
