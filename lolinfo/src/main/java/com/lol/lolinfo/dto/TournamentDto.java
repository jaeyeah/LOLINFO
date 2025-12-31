package com.lol.lolinfo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TournamentDto {

	private Integer tournamentId;
	private String tournamentName;
	private Integer tournamentYear;
	private LocalDateTime tournamentStart;
	private LocalDateTime tournamentEnd;
	private String tournamentIsofficial;
	private String tournamentTierType;
}
