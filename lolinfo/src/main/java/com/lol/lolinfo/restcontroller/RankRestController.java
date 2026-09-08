package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.service.CkRankService;
import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingResponseVO;

@CrossOrigin
@RestController
@RequestMapping("/api/rank")
public class RankRestController {

	@Autowired
    private CkRankService ckRankService;

    @GetMapping("/ck/streak")
    public CkStreakRankingResponseVO ckStreakRanking(
            @RequestParam(defaultValue = "current") String type,
            @RequestParam(defaultValue = "10") int limit) {

        return ckRankService.getStreakRanking(type, limit);
    }

    @GetMapping("/ck/win")
    public List<CkRankingVO> ckWinRanking(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "10") int limit) {

        return ckRankService.getWinRanking(period, year, limit);
    }

    @GetMapping("/ck/win-rate")
    public List<CkRankingVO> ckWinRateRanking(
            @RequestParam(defaultValue = "30") int minPlayCount,
            @RequestParam(defaultValue = "10") int limit) {

        return ckRankService.getWinRateRanking(minPlayCount, limit);
    }
}