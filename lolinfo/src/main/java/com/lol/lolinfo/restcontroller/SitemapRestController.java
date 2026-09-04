package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.SitemapDao;
import com.lol.lolinfo.vo.SitemapVO;

@RestController
@RequestMapping("/sitemap.xml")
public class SitemapRestController {

    @Autowired
    private SitemapDao sitemapDao;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public String sitemap() {

        List<SitemapVO> streamerList = sitemapDao.selectStreamerList();
        List<SitemapVO> tournamentList = sitemapDao.selectTournamentList();

        StringBuilder xml = new StringBuilder();

        xml.append("""
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
            """);

        // 메인
        appendUrl(xml, "https://sooplol.com/");

        // 기본 페이지
        appendUrl(xml, "https://sooplol.com/streamer");
        appendUrl(xml, "https://sooplol.com/ck");
        appendUrl(xml, "https://sooplol.com/tournament");
        appendUrl(xml, "https://sooplol.com/board");
        
        // 사이트 정책/안내
        appendUrl(xml, "https://sooplol.com/privacy");
        appendUrl(xml, "https://sooplol.com/terms");
        
        // 스트리머 상세
        for (SitemapVO streamer : streamerList) {

            String baseUrl =
                "https://sooplol.com/streamer/" + streamer.getTargetNo();

            appendUrl(xml, baseUrl);
            appendUrl(xml, baseUrl + "/tournaments");
            appendUrl(xml, baseUrl + "/ck-records");
            appendUrl(xml, baseUrl + "/streamerWith");
        }

        // 대회 상세
        for (SitemapVO tournament : tournamentList) {

            appendUrl(
                xml,
                "https://sooplol.com/tournament/" + tournament.getTargetNo()
            );
        }

        xml.append("</urlset>");

        return xml.toString();
    }

    private void appendUrl(StringBuilder xml, String url) {

        xml.append("""
            <url>
                <loc>%s</loc>
            </url>
            """.formatted(url));
    }
}