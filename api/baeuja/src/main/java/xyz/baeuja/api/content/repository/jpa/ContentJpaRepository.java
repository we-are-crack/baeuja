package xyz.baeuja.api.content.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Content;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ContentJpaRepository {

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
}
