package xyz.baeuja.api.content.repository.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import xyz.baeuja.api.content.domain.Word;
import xyz.baeuja.api.global.util.annotation.RepositoryTest;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class WordJpaRepositoryTest {

    @Autowired
    WordJpaRepository wordRepository;

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

    @Test
    @DisplayName("전체 word id 조회")
    void findAllIds_success() {
        // when
        List<Long> findIds = wordRepository.findAllIds();

        // then
        assertThat(findIds).isNotEmpty().contains(1L);
    }

    @Test
    @DisplayName("id list를 포함한 word list 조회 성공")
    void findAllInIds_success() {
        // given
        List<Long> includeIds = LongStream.rangeClosed(1, 5).boxed().toList();

        // when
        List<Word> findWords = wordRepository.findAllInIds(includeIds).get();

        // then
        assertThat(findWords).hasSize(includeIds.size());
    }
}