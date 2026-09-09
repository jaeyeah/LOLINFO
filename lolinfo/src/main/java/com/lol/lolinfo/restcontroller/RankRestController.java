package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.service.RankService;
import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingResponseVO;
import com.lol.lolinfo.vo.MyeolmangRankingVO;

@CrossOrigin
@RestController
@RequestMapping("/api/rank")
public class RankRestController {

	@Autowired
    private RankService rankService;

    @GetMapping("/ck/streak")
    public CkStreakRankingResponseVO ckStreakRanking(
            @RequestParam(defaultValue = "current") String type,
            @RequestParam(defaultValue = "10") int limit) {
        return rankService.getStreakRanking(type, limit);
    }

    @GetMapping("/ck/win")
    public List<CkRankingVO> ckWinRanking(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "10") int limit) {
        return rankService.getWinRanking(period, year, limit);
    }

    @GetMapping("/ck/win-rate")
    public List<CkRankingVO> ckWinRateRanking(
            @RequestParam(defaultValue = "30") int minPlayCount,
            @RequestParam(defaultValue = "10") int limit) {
        return rankService.getWinRateRanking(minPlayCount, limit);
    }
    
    //멸망전 랭킹
    @GetMapping("/myeolmang/result")
    public List<MyeolmangRankingVO> myeolmangRanking(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(defaultValue = "20") int limit) {
        return rankService.getMyeolmangRanking(period, limit);
    }
    
}