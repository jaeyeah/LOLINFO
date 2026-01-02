package com.lol.lolinfo.dto;

import java.time.LocalDate;

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
	private LocalDate tournamentStart;
	private LocalDate tournamentEnd;
	private String tournamentIsofficial;
	private String tournamentTierType;
}
