package com.lol.lolinfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerDto {

	private int streamerNo;
	private String streamerSoopId;
	private String streamerName;
	private String streamerProfileUrl;
}
