package xyz.baeuja.api.content.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.SentenceWord;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SentenceWordJpaRepository {

    private final EntityManager em;

    /**
     * sentenceWord 저장
     *
     * @param sentenceWord 저장할 sentenceWord
     * @return sentenceWord id
     */
    public Long save(SentenceWord sentenceWord) {
        em.persist(sentenceWord);
        return sentenceWord.getId();
    }

    /**
     * id 로 SentenceWord 조회
     *
     * @param id 조회할 sentenceWord id
     * @return null이 아닌 경우 SentenceWord를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<SentenceWord> findOne(long id) {
        return Optional.ofNullable(em.find(SentenceWord.class, id));
    }
}
