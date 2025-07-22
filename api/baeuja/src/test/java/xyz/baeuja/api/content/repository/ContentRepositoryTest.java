package xyz.baeuja.api.content.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.home.dto.HomeContentResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class ContentRepositoryTest {

    @Autowired ContentRepository contentRepository;

    @Test
    @DisplayName("최근에 추가된 10개 content 정보 조회")
    void findTop10ByOrderByCreatedAtDesc_success() {
        // when
        List<HomeContentResponse> latestContents = contentRepository.findTop10ByOrderByCreatedAtDesc();

        // then
        assertThat(latestContents).hasSize(10);
        assertThat(latestContents.get(0).getUnitCount()).isNotZero();
        assertThat(latestContents.get(0).getWordCount()).isNotZero();
    }
}