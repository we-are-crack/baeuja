package xyz.baeuja.api.content.repository.query;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.home.dto.HomeContentsResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/truncate_all.sql",
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class ContentQueryRepositoryTest {

    @Autowired ContentQueryRepository contentRepository;

    @Test
    @DisplayName("최근에 추가된 10개 content 정보 조회")
    void findTop10ByOrderByCreatedAtDesc_success() {
        // when
        List<HomeContentsResponse> latestContents = contentRepository.findHomeContents(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updatedAt")));

        // then
        assertThat(latestContents).hasSize(10);
        assertThat(latestContents.get(0).getUnitCount()).isNotZero();
        assertThat(latestContents.get(0).getWordCount()).isNotZero();
    }
}