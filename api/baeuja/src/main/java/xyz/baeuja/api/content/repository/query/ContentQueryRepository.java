package xyz.baeuja.api.content.repository.query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.baeuja.api.content.domain.Content;
import xyz.baeuja.api.home.dto.HomeContentsResponse;

import java.util.List;

public interface ContentQueryRepository extends JpaRepository<Content, Long> {

    /**
     * home 화면에서 최신 콘텐츠 10개 조회
     *
     * @param pageable 페이징 정보 (page = 0, size = 10, sort = updatedAt DESC)
     * @return HomeContentsResponse list
     */
    @Query("""
            select new xyz.baeuja.api.home.dto.HomeContentsResponse(
                c.id, c.classification, c.title, c.artist, c.director, c.thumbnailUrl,
                (select count(u) from Unit u where u.content.id = c.id),
                (select count(sw.word) from SentenceWord sw where sw.sentence.unit.content.id = c.id)
            ) from Content c
            """)
    List<HomeContentsResponse> findHomeContents(Pageable pageable);



}
