package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.HostDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HostListVO {

	private Integer hostStreamer;
	private Integer hostTournament;
	private String streamerSoopId;
	private String streamerName;
	private String tournamentName;
	private int tournamentYear;
	private String tournamentTierType;
}
