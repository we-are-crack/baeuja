package we_are_crack.baeuja.user.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import we_are_crack.baeuja.user.domain.User;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public boolean existsByEmail(String email) {
        Long count = em.createQuery("select count(u) from User u where u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    public boolean existsByNickname(String nickname) {
        Long count =  em.createQuery("select count(u) from User u where u.nickname = :nickname", Long.class)
                .setParameter("nickname", nickname)
                .getSingleResult();

        return count > 0;
    }
}
