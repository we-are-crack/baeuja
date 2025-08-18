package xyz.baeuja.api.content.repository.query;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.helper.TestDataHelper;
import xyz.baeuja.api.learning.dto.unit.LearningUnitResponse;
import xyz.baeuja.api.user.domain.User;

import java.util.List;

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
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
class UnitQueryRepositoryTest {

    @Autowired
    UnitQueryRepository unitQueryRepository;

    @Autowired
    TestDataHelper testDataHelper;

    User user;

    @BeforeEach
    void setUp() {
        testDataHelper.clearAll();
        user = testDataHelper.saveGuestUser();
    }

    @Test
    @DisplayName("학습 유닛 리스트 조회 성공")
    void findLearningUnit_success() {
        // given
        Long contentId = 1L;

        // when
        List<LearningUnitResponse> findLearningUnits =
                unitQueryRepository.findLearningUnit(contentId, user.getId());

        // then
        assertThat(findLearningUnits).isNotEmpty();
        assertThat(findLearningUnits.get(0).getId()).isEqualTo(1);
        assertThat(findLearningUnits.get(0).getSentencesCount()).isNotZero();
        assertThat(findLearningUnits.get(0).getWordsCount()).isNotZero();
        assertThat(findLearningUnits.get(0).getProgressRate()).isZero();
        assertThat(findLearningUnits.get(0).getLastLearned()).isEmpty();
    }

    @Test
    @DisplayName("학습 유닛 리스트 조회 성공 - 빈 리스트 반환")
    void findLearningUnit_empty_success() {
        // given
        Long contentId = 0L;

        // when
        List<LearningUnitResponse> findLearningUnits =
                unitQueryRepository.findLearningUnit(contentId, user.getId());

        // then
        assertThat(findLearningUnits).isEmpty();
    }
}