package xyz.baeuja.api.content.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Sentence;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SentenceRepository {

    private final EntityManager em;

    /**
     * sentence 저장
     *
     * @param sentence 저장할 sentence
     * @return sentence id
     */
    public Long save(Sentence sentence) {
        em.persist(sentence);
        return sentence.getId();
    }

    /**
     * id 로 sentence 조회
     *
     * @param id 조회할 sentence id
     * @return null이 아닌 경우 Sentence를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<Sentence> findOne(long id) {
        return Optional.ofNullable(em.find(Sentence.class, id));
    }
}
