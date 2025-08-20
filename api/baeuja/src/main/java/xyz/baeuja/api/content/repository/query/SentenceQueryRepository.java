package xyz.baeuja.api.content.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.baeuja.api.content.domain.Sentence;
import xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto;

import java.util.List;

public interface SentenceQueryRepository extends JpaRepository<Sentence, Long> {

    /**
     * 학습 유닛 리스트에 포함된 학습 문장들 중, 명대사 또는 회화 표현인 학습 문장들을 RepresentativeSentenceDto에 담아서 조회. <br>
     * 이후, Map<Long, RepresentativeSentenceDto> 으로 매핑.
     *
     * @param unitIds 학습 유닛 리스트의 unit id list
     * @param userId user id
     * @return RepresentativeSentenceDto list
     */
    @Query("""
            select new xyz.baeuja.api.learning.dto.sentence.RepresentativeSentenceDto(
               s.unit.id,
               s.id, s.korean, s.english, s.isConversation, s.isFamousLine, usb.isBookmark
            )
            from Sentence s
            left join UserSentenceBookmark usb
                on s.id = usb.sentence.id and usb.user.id = :userId
            where s.unit.id in :unitIds and (s.isFamousLine = true or s.isConversation = true)
            """)
    List<RepresentativeSentenceDto> findSentencesWithBookmarkBatch(
            @Param("userId") Long userId,
            @Param("unitIds") List<Long> unitIds
    );
}
