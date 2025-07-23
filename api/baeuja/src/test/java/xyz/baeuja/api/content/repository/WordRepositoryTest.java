package xyz.baeuja.api.content.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.content.domain.Word;

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
class WordRepositoryTest {

    @Autowired
    WordRepository wordRepository;

    @Test
    void findOne_success() {
        // given
        Long id = 1L;

        // when
        Word word = wordRepository.findOne(id).orElseGet(null);

        // then
        assertThat(word).isNotNull();
        assertThat(word.getId()).isEqualTo(id);
    }
}