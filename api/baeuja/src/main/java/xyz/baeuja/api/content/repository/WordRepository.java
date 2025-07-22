package xyz.baeuja.api.content.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Word;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordRepository {

    private final EntityManager em;

    /**
     * word 저장
     *
     * @param word 저장할 word
     * @return word id
     */
    public Long save(Word word) {
        em.persist(word);
        return word.getId();
    }

    /**
     * id 로 word 조회
     *
     * @param id 조회할 word id
     * @return null이 아닌 경우 Word를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<Word> findOne(long id) {
        return Optional.ofNullable(em.find(Word.class, id));
    }
}
