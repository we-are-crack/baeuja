package xyz.baeuja.api.content.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.baeuja.api.content.domain.Unit;
import xyz.baeuja.api.learning.dto.unit.LearningUnitResponse;

import java.util.List;

public interface UnitQueryRepository extends JpaRepository<Unit, Long> {

    /**
     * 학습 유닛 조회. 사용자 활동 정보도 함께 조회.
     *
     * @param contentId 조회할 유닛이 속한 content id
     * @param userId 앱을 사용중인 사용자의 user id
     * @return LearningUnitResponse list
     */
    @Query("""
            select new xyz.baeuja.api.learning.dto.unit.LearningUnitResponse(
                u.id, u.thumbnailUrl,
                (select count(s) from Sentence s where s.unit.id = u.id),
                (select count(sw) from SentenceWord sw where sw.sentence.unit.id = u.id),
                uua.progressRate, uua.updatedAt
            )
            from Unit u
            left join UserUnitActivity uua
                on u.id = uua.unit.id and uua.user.id = :userId
            where u.content.id = :contentId
            order by u.id
            """)
    List<LearningUnitResponse> findLearningUnit(
            @Param("contentId") Long contentId,
            @Param("userId") Long userId
    );
}
