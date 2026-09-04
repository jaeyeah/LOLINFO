package com.lol.lolinfo.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FeedbackDto {

	private Integer feedbackId;
	private String feedbackVisitorId;
	private String feedbackTargetType;
	private Integer feedbackTargetId;
	private String feedbackType;
	private String feedbackContent;
	private String feedbackUrl;
	private String feedbackStatus;
	private Timestamp feedbackCreated;
}
