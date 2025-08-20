package xyz.baeuja.api.content.repository.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto;
import xyz.baeuja.api.user.domain.User;

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
class SentenceQueryRepositoryTest {

    @Autowired
    SentenceQueryRepository sentenceQueryRepository;

    @Autowired
    TestDataHelper testDataHelper;

    User user;

    @BeforeEach
    void setUp() {
        testDataHelper.clearAll();
        user = testDataHelper.saveGuestUser();
    }

    @Test
    @DisplayName("즐겨찾기를 포함한 문장 요약 정보 조회 성공")
    void findSentencesWithBookmarkBatch() {
        //given
        List<Long> unitIds = List.of(54L, 55L, 56L); // 사랑의 불시착

        //when
        List<RepresentativeSentenceDto> findSentences = sentenceQueryRepository.findSentencesWithBookmarkBatch(user.getId(), unitIds);

        //then
        assertThat(findSentences).isNotEmpty();
    }
}