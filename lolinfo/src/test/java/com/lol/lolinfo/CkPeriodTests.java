package com.lol.lolinfo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.error.ErrorRestControllerAdvice;
import com.lol.lolinfo.restcontroller.CkRestController;
import com.lol.lolinfo.service.CkService;
import com.lol.lolinfo.vo.CkPeriodVO;
import com.lol.lolinfo.vo.CkListVO;

class CkPeriodTests {
    @Test
    void acceptsAllTimeSingleDayAndLeapDay() {
        assertNull(new CkPeriodVO(1, 1, null, null).getStartDate());
        assertEquals("2026-09-07", new CkPeriodVO(1, 1, "2026-09-07", "2026-09-07").getEndDate());
        assertDoesNotThrow(() -> new CkPeriodVO(1, 1, "2024-02-29", "2024-03-01"));
    }

    @Test
    void rejectsPartialReversedMalformedAndImpossiblePeriods() {
        String[][] invalid = {
            {null, "2026-09-07"}, {"2026-09-07", null}, {"", ""},
            {"2026-09-08", "2026-09-07"}, {"2026-02-29", "2026-03-01"},
            {"2026-9-01", "2026-09-07"}, {"0000-01-01", "2026-09-07"},
            {"2026-09-07", "9999-12-31"}
        };
        for (String[] range : invalid) {
            assertThrows(IllegalArgumentException.class, () -> new CkPeriodVO(1, 1, range[0], range[1]));
        }
        assertThrows(IllegalArgumentException.class, () -> new CkPeriodVO(1, 0, null, null));
    }

    @Test
    void countAndListSharePeriodAndClampPageAfterFiltering() {
        AtomicInteger count = new AtomicInteger(11);
        List<CkPeriodVO> countQueries = new ArrayList<>();
        List<CkPeriodVO> listQueries = new ArrayList<>();
        CkDao dao = new CkDao() {
            @Override public int countByStreamer(CkPeriodVO query) {
                countQueries.add(query);
                return count.get();
            }
            @Override public List<CkListVO> selectListByStreamer(CkPeriodVO query) {
                listQueries.add(query);
                return List.of();
            }
        };
        CkService service = new CkService();
        ReflectionTestUtils.setField(service, "ckDao", dao);
        CkPeriodVO query = new CkPeriodVO(1, 99, "2026-09-01", "2026-09-07");
        service.selectListByStreamer(query);
        assertEquals(2, query.getPage());
        assertEquals(11, query.getBegin());
        assertSame(query, countQueries.get(0));
        assertSame(query, listQueries.get(0));

        count.set(0);
        service.selectListByStreamer(query);
        assertEquals(1, query.getPage());
        assertEquals(0, query.getTotalPage());
    }

    @Test
    void invalidPeriodReturns400BeforeServiceOrVisitCounter() throws Exception {
        CkRestController controller = new CkRestController();
        // 검증 전에 서비스가 호출되면 DAO가 없으므로 400 대신 오류가 발생한다.
        CkService service = new CkService();
        AtomicInteger visitCalls = new AtomicInteger();
        VisitUseDao visits = new VisitUseDao() {
            @Override public void increase(String type) { visitCalls.incrementAndGet(); }
        };
        ReflectionTestUtils.setField(controller, "ckService", service);
        ReflectionTestUtils.setField(controller, "visitUseDao", visits);
        var mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorRestControllerAdvice()).build();
        for (String path : List.of("/api/ck/streamer/1", "/api/ck/1/vs")) {
            mvc.perform(get(path).param("startDate", "2026-09-08").param("endDate", "2026-09-07"))
                    .andExpect(status().isBadRequest());
            mvc.perform(get(path).param("startDate", "2026-09-01"))
                    .andExpect(status().isBadRequest());
        }
        mvc.perform(get("/api/ck/streamer/1").param("page", "not-a-number"))
                .andExpect(status().isBadRequest());
        assertEquals(0, visitCalls.get());
    }

    @Test
    void mapperUsesSameInclusivePeriodBeforePaginationAndAggregation() throws Exception {
        Configuration config = new Configuration();
        config.getTypeAliasRegistry().registerAliases("com.lol.lolinfo.dto");
        config.getTypeAliasRegistry().registerAliases("com.lol.lolinfo.vo");
        try (InputStream stream = getClass().getResourceAsStream("/mybatis/ck-mapper.xml")) {
            new XMLMapperBuilder(stream, config, "mybatis/ck-mapper.xml", config.getSqlFragments()).parse();
        }
        for (String id : List.of("selectListByStreamer", "countByStreamer", "selectVsList")) {
            var statement = config.getMappedStatement("ck." + id);
            String filtered = statement.getBoundSql(new CkPeriodVO(1, 1, "2026-09-01", "2026-09-07"))
                    .getSql().replaceAll("\\s+", " ");
            assertTrue(filtered.contains("ck_date >= to_date(?, 'YYYY-MM-DD')"));
            assertTrue(filtered.contains("ck_date < to_date(?, 'YYYY-MM-DD') + 1"));
            if (id.equals("selectListByStreamer")) assertTrue(filtered.indexOf("ck_date >=") < filtered.indexOf("where rn between"));
            if (id.equals("selectVsList")) assertTrue(filtered.indexOf("ck_date >=") < filtered.indexOf("group by"));
            String allTime = statement.getBoundSql(new CkPeriodVO(1, 1, null, null)).getSql();
            assertFalse(allTime.contains("to_date"));
        }
    }
}
