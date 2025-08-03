package xyz.baeuja.api.content.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Word;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordRepository {

    private final EntityManager em;

    /**
     * Word 저장
     *
     * @param word 저장할 Word
     * @return Word id
     */
    public Long save(Word word) {
        em.persist(word);
        return word.getId();
    }

    /**
     * id 로 Word 조회
     *
     * @param id 조회할 word id
     * @return null이 아닌 경우 Word를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<Word> findOne(long id) {
        return Optional.ofNullable(em.find(Word.class, id));
    }

    /**
     * 전체 Word id 조회
     *
     * @return 전체 Word id list
     */
    public List<Long> findAllIds() {
        List<Long> resultList = em.createQuery("select w.id from Word w", Long.class)
                .getResultList();
        return resultList;
    }

    /**
     * ids 에 포함된 Word 조회
     *
     * @param ids 조회할 Word id list
     * @return null이 아닌 경우 Word list를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<List<Word>> findAllInIds(List<Long> ids) {
        return Optional.ofNullable(em.createQuery("select w from Word w where w.id in :ids", Word.class)
                .setParameter("ids", ids)
                .getResultList());
    }
}
