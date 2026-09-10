package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.service.RankService;
import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingResponseVO;
import com.lol.lolinfo.vo.MyeolmangRankingVO;
import com.lol.lolinfo.vo.MyeolmangPositionRankingVO;

@CrossOrigin
@RestController
@RequestMapping("/api/rank")
public class RankRestController {

	@Autowired
    private RankService rankService;
	@Autowired
	private VisitUseDao visitUseDao;

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
    	visitUseDao.increase("ranking");
        return rankService.getWinRanking(period, year, limit);
    }

    @GetMapping("/ck/win-rate")
    public List<CkRankingVO> ckWinRateRanking(
            @RequestParam(defaultValue = "30") int minPlayCount,
            @RequestParam(defaultValue = "10") int limit) {
        return rankService.getWinRateRanking(minPlayCount, limit);
    }
    
    //멸망전 랭킹
    @GetMapping("/myeolmang")
    public ResponseEntity<List<MyeolmangPositionRankingVO>> myeolmangPositionRanking(
            @RequestParam(required = false) String position) {
        try {
            return ResponseEntity.ok(rankService.getMyeolmangPositionRanking(position));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/myeolmang/result")
    public List<MyeolmangRankingVO> myeolmangRanking(
            @RequestParam(defaultValue = "all") String period,
            @RequestParam(defaultValue = "20") int limit) {
        return rankService.getMyeolmangRanking(period, limit);
    }
    
}
