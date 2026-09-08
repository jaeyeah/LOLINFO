package com.lol.lolinfo.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CkStreakRankingResponseVO {

    private List<CkStreakRankingVO> winList;
    private List<CkStreakRankingVO> loseList;
}