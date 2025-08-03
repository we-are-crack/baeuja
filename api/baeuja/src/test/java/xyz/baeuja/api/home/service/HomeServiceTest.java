package xyz.baeuja.api.home.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.content.cache.WordIdCache;
import xyz.baeuja.api.home.dto.HomeRecommendWordsResponse;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/truncate_all.sql",
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest
class HomeServiceTest {

    @Autowired
    HomeService homeService;

    @Autowired
    WordIdCache wordIdCache;

    @BeforeEach
    void setUp() {
        wordIdCache.init();
    }

    @Test
    @DisplayName("학습 추천 단어 조회 성공")
    void getRecommendWords_success() {
        // given
        List<Long> excludeIds = LongStream.rangeClosed(1, 5).boxed().toList();

        // when
        List<HomeRecommendWordsResponse> recommendWords = homeService.getRecommendWords(excludeIds);

        // then
        assertThat(recommendWords).hasSize(5);

    }
}