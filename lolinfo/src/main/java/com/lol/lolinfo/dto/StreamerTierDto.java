package com.lol.lolinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerTierDto {

	private int tournamentNo;
	private int streamerNo;
	private String tierPosition;
	private String tierName;
	
}
