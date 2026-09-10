package com.lol.lolinfo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.error.ErrorRestControllerAdvice;
import com.lol.lolinfo.restcontroller.CkRestController;
import com.lol.lolinfo.service.CkService;
import com.lol.lolinfo.vo.CkBalanceVO;

class CkBalanceTests {
    @Test
    void bothRequestsReachOneSelectWithOptionalExclusionAndSerializeResponse() throws Exception {
        List<Map<?, ?>> calls = new ArrayList<>();
        SqlSession session = (SqlSession) Proxy.newProxyInstance(SqlSession.class.getClassLoader(),
                new Class<?>[] {SqlSession.class}, (proxy, method, args) -> {
                    assertEquals("selectList", method.getName());
                    assertEquals("ck.selectBalanceList", args[0]);
                    calls.add(new HashMap<>((Map<?, ?>) args[1]));
                    return calls.size() == 1
                            ? List.of(new CkBalanceVO(2, "상대B", "MID", 12L, Date.valueOf("2026-09-10")))
                            : List.of();
                });
        CkDao dao = new CkDao();
        ReflectionTestUtils.setField(dao, "sqlSession", session);
        CkService service = new CkService();
        ReflectionTestUtils.setField(service, "ckDao", dao);
        CkRestController controller = new CkRestController();
        ReflectionTestUtils.setField(controller, "ckService", service);
        var mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorRestControllerAdvice()).build();

        mvc.perform(get("/api/ck/balance").param("streamerNo", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].opponentNo").value(2))
                .andExpect(jsonPath("$[0].opponentName").value("상대B"))
                .andExpect(jsonPath("$[0].position").value("MID"))
                .andExpect(jsonPath("$[0].matchCount").value(12))
                .andExpect(jsonPath("$[0].lastMatchDate").value("2026-09-10"));
        mvc.perform(get("/api/ck/balance").param("streamerNo", "2").param("position", "MID")
                .param("baseStreamerNo", "1"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
        assertEquals(2, calls.size());
        assertEquals(1, calls.get(0).get("streamerNo"));
        assertNull(calls.get(0).get("baseStreamerNo"));
        assertNull(calls.get(0).get("position"));
        assertEquals(Map.of("streamerNo", 2, "position", "MID", "baseStreamerNo", 1), calls.get(1));
    }

    @Test
    void rejectsInvalidQueriesBeforeDatabaseAccess() throws Exception {
        CkRestController controller = new CkRestController();
        ReflectionTestUtils.setField(controller, "ckService", new CkService());
        var mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorRestControllerAdvice()).build();
        for (String query : List.of("", "?position=MID",
                "?streamerNo=0&position=MID", "?streamerNo=1&position=",
                "?streamerNo=1&position=JUNGLE", "?streamerNo=1&position=MID&baseStreamerNo=-1",
                "?streamerNo=no&position=MID", "?streamerNo=1&position=MID&baseStreamerNo=no")) {
            mvc.perform(get("/api/ck/balance" + query)).andExpect(status().isBadRequest());
        }
    }

    @Test
    void mapperBindsOptionalExclusionAndUsesSameLaneJoinBeforeAggregation() throws Exception {
        Configuration config = new Configuration();
        config.getTypeAliasRegistry().registerAliases("com.lol.lolinfo.dto");
        config.getTypeAliasRegistry().registerAliases("com.lol.lolinfo.vo");
        try (InputStream stream = getClass().getResourceAsStream("/mybatis/ck-mapper.xml")) {
            new XMLMapperBuilder(stream, config, "mybatis/ck-mapper.xml", config.getSqlFragments()).parse();
        }
        var statement = config.getMappedStatement("ck.selectBalanceList");
        Map<String, Object> query = new HashMap<>(Map.of("streamerNo", 2, "position", "MID"));
        query.put("baseStreamerNo", null);
        query.put("position", null);
        var allPositions = statement.getBoundSql(query);
        String allSql = allPositions.getSql().replaceAll("\\s+", " ");
        assertFalse(allSql.contains("p.ck_position = ?"));
        assertTrue(allSql.contains("p.ck_position as position"));
        assertTrue(allSql.contains("group by opponent.ck_streamer, s.streamer_name, p.ck_position"));
        assertEquals(List.of("streamerNo", "streamerNo"),
                allPositions.getParameterMappings().stream().map(p -> p.getProperty()).toList());
        query.put("position", "MID");
        var first = statement.getBoundSql(query);
        String sql = first.getSql().replaceAll("\\s+", " ");
        assertTrue(sql.contains("opponent.ck_id = p.ck_id"));
        assertTrue(sql.contains("opponent.ck_side != p.ck_side"));
        assertTrue(sql.contains("opponent.ck_position = p.ck_position"));
        assertTrue(sql.contains("join streamer s on s.streamer_no = opponent.ck_streamer"));
        assertTrue(sql.contains("max(c.ck_date) desc nulls last"));
        assertTrue(sql.contains("keep (dense_rank last order by c.ck_date nulls first)"));
        assertFalse(sql.toLowerCase().contains("rownum"));
        assertEquals(List.of("streamerNo", "position", "streamerNo"),
                first.getParameterMappings().stream().map(p -> p.getProperty()).toList());
        query.put("baseStreamerNo", 1);
        var second = statement.getBoundSql(query);
        assertEquals(List.of("streamerNo", "position", "streamerNo", "baseStreamerNo"),
                second.getParameterMappings().stream().map(p -> p.getProperty()).toList());
    }
}
