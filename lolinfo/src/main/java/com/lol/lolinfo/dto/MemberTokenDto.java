package com.lol.lolinfo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder  @NoArgsConstructor @AllArgsConstructor
public class MemberTokenDto {

	private Long memberTokenNo;
	private String memberTokenTarget;
	private String memberTokenValue;
	private LocalDateTime memberTokenTime;
}
