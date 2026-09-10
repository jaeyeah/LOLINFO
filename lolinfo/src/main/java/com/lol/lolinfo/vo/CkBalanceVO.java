package com.lol.lolinfo.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkBalanceVO {
	private Integer opponentNo;
	private String opponentName;
	private String opponentSoopId;
	private String position;
	private Long matchCount;
	private Date lastMatchDate;
}
