package com.lol.lolinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkParticipantDto {
	private Integer ckParticipantId;
	private Integer ckId;
	private Integer ckStreamer;
	private String ckSide; // 레드 vs 블루
	private String ckPosition;
}
