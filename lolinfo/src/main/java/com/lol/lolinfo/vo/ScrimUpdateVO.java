package com.lol.lolinfo.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.CkParticipantDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ScrimUpdateVO {
	private Integer scrimId;
	private Integer scrimRedScore;
	private Integer scrimBlueScore;
	private Date scrimDate;
}
