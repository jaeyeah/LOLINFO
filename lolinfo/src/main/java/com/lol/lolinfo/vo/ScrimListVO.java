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
public class ScrimListVO {
	private int scrimId;
    private int scrimTournament;

    private int scrimRedTeam;
    private String scrimRedName;
    private int scrimRedScore;

    private int scrimBlueTeam;
    private String scrimBlueName;
    private int scrimBlueScore;

    private Date scrimDate;
    private String scrimCreatedBy;

    private Integer scrimWinner;
    private String scrimWinnerName;
}
