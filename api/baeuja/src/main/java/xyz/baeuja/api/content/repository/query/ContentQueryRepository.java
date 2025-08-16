package xyz.baeuja.api.content.repository.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.home.dto.HomeContentsResponse;

import java.util.List;

public interface ContentQueryRepository extends JpaRepository<Content, Long> {

    @Query("""
            select new xyz.baeuja.api.home.dto.HomeContentsResponse(
                c.id, c.classification, c.title, c.artist, c.director, c.thumbnailUrl, 
                (select count(u) from Unit u where u.content.id = c.id),
                (select count(sw.word) from SentenceWord sw where sw.sentence.unit.content.id = c.id)
            ) from Content c
            """)
    public List<HomeContentsResponse> findHomeContents(Pageable pageable);

//    private final EntityManager em;
//
//    /**
//     * home 에서 가장 최근에 추가된 content 를 조회. JPQL 로 SentenceWord 까지 조인
//     *
//     * @return content 정보와 unit 개수, 단어 개수를 포함한 HomeContentResponse 리스트
//     */
//    public List<HomeContentsResponse> findTop10ByOrderByCreatedAtDesc() {
//        return em.createQuery("select new xyz.baeuja.api.home.dto.HomeContentsResponse(" +
//                        "c.id, c.classification, c.title, c.artist, c.director, c.thumbnailUrl, " +
//                        "(select count(u) from Unit u where u.content.id = c.id), " +
//                        "(select count(sw.word) from SentenceWord sw where sw.sentence.unit.content.id = c.id))" +
//                        "from Content c " +
//                        "order by c.createdAt desc", HomeContentsResponse.class)
//                .setMaxResults(10)
//                .getResultList();
//    }
}
