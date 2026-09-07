package com.lol.lolinfo.restcontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.SitemapDao;
import com.lol.lolinfo.vo.SitemapVO;

@RestController
@RequestMapping("/api/sitemap")
public class SitemapRestController {

    @Autowired
    private SitemapDao sitemapDao;

    @GetMapping("/data")
    public Map<String, Object> sitemapData() {
        List<Integer> streamers = sitemapDao.selectStreamerList()
            .stream()
            .map(SitemapVO::getTargetNo)
            .toList();

        List<Integer> tournaments = sitemapDao.selectTournamentList()
            .stream()
            .map(SitemapVO::getTargetNo)
            .toList();

        List<Integer> tournamentStreamers = sitemapDao.selectTournamentStreamerList()
            .stream()
            .map(SitemapVO::getTargetNo)
            .toList();

        List<Integer> ckStreamers = sitemapDao.selectCkStreamerList()
            .stream()
            .map(SitemapVO::getTargetNo)
            .toList();

        return Map.of(
        	    "streamers", streamers,
        	    "tournaments", tournaments,
        	    "tournamentStreamers", tournamentStreamers,
        	    "ckStreamers", ckStreamers
        	);
    }
}