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

    @PostMapping("/rebuild")
    public void rebuild() {
        ckStreakService.rebuildAll();
    }
}