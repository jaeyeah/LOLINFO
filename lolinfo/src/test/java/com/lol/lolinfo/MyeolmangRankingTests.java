package com.lol.lolinfo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mockito.MockMakers;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.lol.lolinfo.configuration.CacheConfiguration;
import com.lol.lolinfo.dao.RankDao;
import com.lol.lolinfo.dao.TeamDao;
import com.lol.lolinfo.dao.TournamentDao;
import com.lol.lolinfo.dto.TeamDto;
import com.lol.lolinfo.dto.TournamentDto;
import com.lol.lolinfo.restcontroller.RankRestController;
import com.lol.lolinfo.service.MyeolmangRankingCacheService;
import com.lol.lolinfo.service.RankService;

class MyeolmangRankingTests {

    @Configuration
    @EnableCaching
    @Import({CacheConfiguration.class, RankDao.class, RankService.class,
            MyeolmangRankingCacheService.class, TeamDao.class, TournamentDao.class})
    static class TestConfig {
        @Bean
        SqlSession sqlSession() {
            SqlSession session = mock(SqlSession.class, withSettings().mockMaker(MockMakers.SUBCLASS));
            when(session.selectList(eq("rank.selectMyeolmangPositionRanking"), any()))
                    .thenReturn(List.of());
            when(session.selectOne("team.sequence")).thenReturn(1);
            when(session.selectOne("tournament.sequence")).thenReturn(1);
            return session;
        }
    }

    @Test
    void normalizesKeysAndCachesEachPositionIncludingEmptyResults() {
        try (var context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            RankService service = context.getBean(RankService.class);
            service.getMyeolmangPositionRanking(null);
            service.getMyeolmangPositionRanking(" ");
            service.getMyeolmangPositionRanking("all");
            for (String position : List.of("TOP", "JUNGLE", "MID", "ADC", "SUPPORT")) {
                service.getMyeolmangPositionRanking(position);
                service.getMyeolmangPositionRanking(" " + position.toLowerCase() + " ");
            }
            verify(context.getBean(SqlSession.class), times(6))
                    .selectList(eq("rank.selectMyeolmangPositionRanking"), any());
            assertThrows(IllegalArgumentException.class,
                    () -> service.getMyeolmangPositionRanking("INVALID"));
        }
    }

    @Test
    void invalidPositionReturnsBadRequest() {
        RankRestController controller = new RankRestController();
        ReflectionTestUtils.setField(controller, "rankService", new RankService());
        assertEquals(400, controller.myeolmangPositionRanking("INVALID").getStatusCode().value());
    }

    @Test
    void teamAndTournamentMutationsEvictAllPositionsWithoutClearingOtherCaches() {
        try (var context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            var manager = context.getBean(CacheManager.class);
            var ranking = manager.getCache("myeolmangRanking");
            var ck = manager.getCache("ckRanking");
            ck.put("existing", "keep");
            TeamDao teams = context.getBean(TeamDao.class);
            TournamentDao tournaments = context.getBean(TournamentDao.class);
            List<Runnable> mutations = List.of(
                    () -> teams.insert(new TeamDto()), () -> teams.update(new TeamDto()),
                    () -> teams.delete(1), () -> tournaments.insert(new TournamentDto()),
                    () -> tournaments.update(new TournamentDto()));
            for (Runnable mutation : mutations) {
                ranking.put("ALL", List.of());
                ranking.put("TOP", List.of());
                mutation.run();
                assertNull(ranking.get("ALL"));
                assertNull(ranking.get("TOP"));
                assertEquals("keep", ck.get("existing", String.class));
            }
        }
    }

    @Test
    void evictionWaitsForCommitAndRollbackKeepsCache() {
        try (var context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            var cache = context.getBean(CacheManager.class).getCache("myeolmangRanking");
            for (boolean commit : List.of(false, true)) {
                cache.put("ALL", List.of());
                TransactionSynchronizationManager.initSynchronization();
                TransactionSynchronizationManager.setActualTransactionActive(true);
                try {
                    context.getBean(MyeolmangRankingCacheService.class).evictAfterCommit();
                    assertNotNull(cache.get("ALL"));
                    for (var synchronization : TransactionSynchronizationManager.getSynchronizations()) {
                        if (commit) synchronization.afterCommit();
                        synchronization.afterCompletion(commit ? TransactionSynchronization.STATUS_COMMITTED
                                : TransactionSynchronization.STATUS_ROLLED_BACK);
                    }
                    assertEquals(commit, cache.get("ALL") == null);
                }
                finally {
                    TransactionSynchronizationManager.clearSynchronization();
                    TransactionSynchronizationManager.setActualTransactionActive(false);
                }
            }
        }
    }

    @Test
    void mybatisParsesMapperAndBindsAllSixPositionKeys() throws Exception {
        var configuration = new org.apache.ibatis.session.Configuration();
        configuration.getTypeAliasRegistry().registerAliases("com.lol.lolinfo.vo");
        try (Reader reader = Resources.getResourceAsReader("mybatis/rank-mapper.xml")) {
            new XMLMapperBuilder(reader, configuration, "mybatis/rank-mapper.xml",
                    configuration.getSqlFragments()).parse();
        }
        var statement = configuration.getMappedStatement("rank.selectMyeolmangPositionRanking");
        for (String position : List.of("ALL", "TOP", "JUNGLE", "MID", "ADC", "SUPPORT")) {
            var sql = statement.getBoundSql(Map.of("position", position));
            assertEquals(6, sql.getParameterMappings().size());
            assertTrue(sql.getParameterMappings().stream().allMatch(p -> p.getProperty().equals("position")));
            assertFalse(sql.getSql().contains("${"));
        }
    }
}
