package xyz.baeuja.api.content.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.content.domain.Unit;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UnitJpaRepository {

    private final EntityManager em;

    /**
     * unit 저장
     *
     * @param unit 저장할 unit
     * @return unit id
     */
    public Long save(Unit unit) {
        em.persist(unit);
        return unit.getId();
    }

    /**
     * id 로 unit 조회
     *
     * @param id 조회할 unit id
     * @return null이 아닌 경우 Unit을 포함한 Optional, 아니면 빈 Optional
     */
    public Optional<Unit> findOne(long id) {
        return Optional.ofNullable(em.find(Unit.class, id));
    }
}
