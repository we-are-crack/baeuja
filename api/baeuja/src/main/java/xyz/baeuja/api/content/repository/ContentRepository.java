package xyz.baeuja.api.content.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.home.dto.HomeContentResponse;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContentRepository {

    private final EntityManager em;

    /**
     * content 저장
     *
     * @param content 저장할 content
     * @return content id
     */
    public Long save(Content content) {
        em.persist(content);
        return content.getId();
    }

    /**
     * id 로 content 조회
     *
     * @param id 조회할 content id
     * @return null이 아닌 경우 Content를 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<Content> findOne(long id) {
        return Optional.ofNullable(em.find(Content.class, id));
    }

    /**
     * home 에서 가장 최근에 추가된 content 를 조회. JPQL 로 SentenceWord 까지 조인
     *
     * @return content 정보와 unit 개수, 단어 개수를 포함한 HomeContentResponse 리스트
     */
    public List<HomeContentResponse> findTop10ByOrderByCreatedAtDesc() {
        return em.createQuery("select new xyz.baeuja.api.home.dto.HomeContentResponse(" +
                        "c.classification, c.title, c.artist, c.director, c.thumbnailUrl, " +
                        "(select count(u) from Unit u where u.content.id = c.id), " +
                        "(select count(sw.word) from SentenceWord sw where sw.sentence.unit.content.id = c.id))" +
                        "from Content c " +
                        "order by c.createdAt desc", HomeContentResponse.class)
                .setMaxResults(10)
                .getResultList();
    }
}
