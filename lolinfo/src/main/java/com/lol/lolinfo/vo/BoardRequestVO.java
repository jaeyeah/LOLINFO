package com.lol.lolinfo.vo;

import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.dto.CkScheduleDto;

import lombok.Data;

@Data
public class BoardRequestVO {

    private BoardDto board;
    private CkScheduleDto schedule;

}