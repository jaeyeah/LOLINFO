package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerWithTournamentVO {
	
	private int partnerNo;
	private String partnerName;
	private String partnerSoopId;
	private int playCount;
	private String withOfficial; // 같이 멸망전 참가이력 여부
	private String withChampion; // 같이 우승이력 여부
	private String withFinal; // 같이 준우승이력 여부
	
	private Integer totalCount;
}
