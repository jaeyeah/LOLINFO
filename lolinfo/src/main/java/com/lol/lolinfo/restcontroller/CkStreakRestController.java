package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.CkStreakDao;
import com.lol.lolinfo.dto.CkStreakDto;
import com.lol.lolinfo.service.CkStreakService;

@RestController
@RequestMapping("/api/ck/streak")
@CrossOrigin
public class CkStreakRestController {

    @Autowired
    private CkStreakService ckStreakService;
    @Autowired
    private CkStreakDao ckStreakDao;
    

    // 스트리머 연승 정보
    @GetMapping("/{streamerNo}")
    public CkStreakDto selectOne(
            @PathVariable Integer streamerNo) {
        return ckStreakDao.selectOne(streamerNo);
    }

    // 현재 연승 랭킹
    @GetMapping("/ranking/current")
    public List<CkStreakDto> currentRanking() {
        return ckStreakDao.selectCurrentWinRanking();
    }

    // 역대 최고 연승 랭킹
    @GetMapping("/ranking/max")
    public List<CkStreakDto> maxRanking() {
        return ckStreakDao.selectMaxWinRanking();
    }
    
    @PostMapping("/rebuild")
    public void rebuild() {
        ckStreakService.rebuildAll();
    }
}