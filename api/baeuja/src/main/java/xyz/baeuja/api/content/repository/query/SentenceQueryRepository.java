package xyz.baeuja.api.content.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.baeuja.api.content.domain.Sentence;
import xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto;

public interface SentenceQueryRepository extends JpaRepository<Sentence, Long> {

    /**
     * 학습 유닛 리스트 조회 시, MOVIE 또는 DRAMA 의 경우 화면에 표시될 대표 문장 요약 정보 및 즐겨찾기 여부 조회.
     *
     * @param unitId 문장이 포함된 unit id
     * @param userId user id
     * @return SentenceSummaryDto
     */
    @Query("""
            select new xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto(
                s.id, s.korean, s.english, s.isConversation, s.isFamousLine, usb.isBookmark
            )
            from Sentence s
            left join UserSentenceBookmark usb
            on s.id = usb.sentence.id and usb.user.id = :userId
            where s.unit.id = :unitId and (s.isFamousLine = true or s.isConversation = true)
            """)
    RepresentativeSentenceDto findSentenceWithBookmark(
            @Param("unitId") Long unitId,
            @Param("userId") Long userId
    );
}
