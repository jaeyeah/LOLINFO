package com.lol.lolinfo.vo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CkScheduleHomeVO {

    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime ckDate;
    private String ckUrl;
}