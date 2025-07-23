package xyz.baeuja.api.content.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.home.dto.HomeRecommendWordsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest
class WordQueryRepositoryTest {

    @Autowired
    WordQueryRepository wordQueryRepository;

    @Test
    @DisplayName("랜덤 단어 조회 - 제외할 단어 없음")
    void findRandomWords_non_exclude() {
        // given
        List<Long> excludeIds = new ArrayList<>();
        int limit = 5;

        // when
        List<HomeRecommendWordsResponse> randomWords = wordQueryRepository.findRandomWords(excludeIds, limit);

        // then
        assertThat(randomWords).hasSize(5);
    }

    @Test
    @DisplayName("랜덤 단어 조회 - 제외할 단어 제공")
    void findRandomWords_exclude() {
        // given
        List<Long> excludeIds = LongStream.rangeClosed(1, 10).boxed().toList();
        int limit = 5;

        // when
        List<HomeRecommendWordsResponse> randomWords = wordQueryRepository.findRandomWords(excludeIds, limit);

        // then
        assertThat(randomWords).hasSize(5);
    }

    @Test
    @DisplayName("랜덤 단어 조회 - 모든 단어 조회 후 빈 리스트 반환")
    void findRandomWords_empty() {
        // given
        List<Long> excludeIds = LongStream.rangeClosed(1, 682).boxed().toList();
        int limit = 5;

        // when
        List<HomeRecommendWordsResponse> randomWords = wordQueryRepository.findRandomWords(excludeIds, limit);

        // then
        assertThat(randomWords).isEmpty();
    }
}