package com.lol.lolinfo.restcontroller;

import java.time.Year;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.service.StatService;
import com.lol.lolinfo.vo.stat.StatMonthlyVO;

@RestController
@RequestMapping("/api/stats")
public class StatRestController {

    @Autowired
    private StatService statService;

    @GetMapping("/monthly")
    public StatMonthlyVO monthlyStat(
            @RequestParam(required = false) Integer year) {

        int targetYear = year != null
            ? year
            : Year.now(ZoneId.of("Asia/Seoul")).getValue();

        return statService.getMonthlyStat(targetYear);
    }
}