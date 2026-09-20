package com.lol.lolinfo.restcontroller;

import java.time.Year;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.service.StatService;
import com.lol.lolinfo.service.StreamerStatService;
import com.lol.lolinfo.vo.stat.StatMonthlyVO;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO;

@RestController
@RequestMapping("/api/stats")
public class StatRestController {

    @Autowired
    private StatService statService;
    @Autowired
    private VisitUseDao visitUseDao;
    @Autowired
    private StreamerStatService streamerStatService;


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> invalidStreamerStat(ResponseStatusException error) {
        return ResponseEntity.status(error.getStatusCode()).body(Map.of("message", error.getReason()));
    }

    @GetMapping("/streamer/{streamerNo}/monthly")
    public StreamerMonthlyStatVO streamerMonthlyStat(@PathVariable int streamerNo,
            @RequestParam(required = false) Integer year) {
        visitUseDao.increase("monthlyStat");
        return streamerStatService.getMonthlyStat(streamerNo,
            year != null ? year : Year.now(ZoneId.of("Asia/Seoul")).getValue());
    }

    @GetMapping("/monthly")
    public StatMonthlyVO monthlyStat(
            @RequestParam(required = false) Integer year) {

        int targetYear = year != null
            ? year
            : Year.now(ZoneId.of("Asia/Seoul")).getValue();
        return statService.getMonthlyStat(targetYear);
    }
}