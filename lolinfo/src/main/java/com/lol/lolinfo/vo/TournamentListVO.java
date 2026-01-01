package com.lol.lolinfo.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TournamentListVO {

	private Integer tournamentId;
	private String tournamentName;
	private Integer tournamentYear;
	private LocalDateTime tournamentStart;
	private LocalDateTime tournamentEnd;
	private String tournamentIsofficial;
	private String tournamentTierType;
	private String topId;
	private String jugId;
	private String midId;
	private String adId;
	private String supId;
	private String topName;
	private String jugName;
	private String midName;
	private String adName;
	private String supName;

}
