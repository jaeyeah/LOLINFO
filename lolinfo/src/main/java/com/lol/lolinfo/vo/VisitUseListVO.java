package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitUseListVO {

	private String useDate;
    private int tournamentDetail;
    private int tournamentList;
    private int streamerDetail;
    private int streamerList;
    private int ckStreamer;
    private int ckList;
}
