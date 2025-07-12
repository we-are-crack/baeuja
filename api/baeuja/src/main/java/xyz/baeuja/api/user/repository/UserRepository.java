package xyz.baeuja.api.user.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.baeuja.api.user.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    /**
     * 사용자 저장
     */
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    /**
     * 사용자 단일 조회
     */
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    /**
     * 이메일로 사용자 단일 조회
     * @param email String
     * @return Optional<User>
     */
    public Optional<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList()
                .stream().findAny();
    }

    /**
     * email 을 가진 사용자 존재 여부
     */
    public boolean existsByEmail(String email) {
        Long count = em.createQuery("select count(u) from User u where u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    /**
     * nickname 을 가진 사용자 존재 여부
     */
    public boolean existsByNickname(String nickname) {
        Long count = em.createQuery("select count(u) from User u where u.nickname = :nickname", Long.class)
                .setParameter("nickname", nickname)
                .getSingleResult();

        return count > 0;
    }
}
